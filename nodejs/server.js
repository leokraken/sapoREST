var express = require('express');
var bodyParser = require("body-parser");
var app = express();
var assert = require('assert');
var unirest = require('unirest');
//var WebSocket = require('ws');

var wordNet = require('wordnet-magic');

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());




/*CONFIG*/
var url = "mongodb://tsi2:tsi2@ds043962.mongolab.com:43962/krakenmongo";
var WS_PORT = 8080;
var SAPO_HOST = "sapo-backendrs.rhcloud.com";
//var SAPO_HOST = 'localhost';
var ML_API = 'https://api.mercadolibre.com';


/*WEBSOCKET CLIENT*/

//Listar las categorias, el siguiente paso es buscar productos por categorias.
app.get('/mercadolibre/categorias', function(req,res){
	var Request = unirest.get('https://api.mercadolibre.com/sites/MLU/categories')
			.type('json')
			.end(function (response) {
			   console.log(response.body);
			   res.send(response.body);
			});	
   }
);

//Buscar productos por categorias.
/*
{
   "category": "MLU1000",
   "limit":10,
   "offset": 1
}
*/
app.post('/mercadolibre/search', function(req,res){
	var category = req.body.category;
	var limit = req.body.limit;
	var offset = req.body.offset;
	console.log(category+limit+offset);
	var Request = unirest.get(ML_API+'/sites/MLU/search?category='+category+'&limit='+limit+'&offset='+offset)
			.type('json')
			.end(function (response) {
			  console.log(response.body);
			  res.send(response.body);
			});	
}
);

//Dada una lista de productos los agrega a la base mongo y postgresql.
//[{"id":"MLU430098893", "generico": false, "categoria":1}]
app.post('/mercadolibre/addproductos', function(req,res){
	var req1 = req;

	function dorequest(index){
		var Request = unirest.get(ML_API+'/items/'+req.body[index].id)
			      .type('json')
			      .end(function (response) {
					//java rest
					var response1 = response;
					var dataproducto= {
							"nombre":response.body.title,
							"descripcion": "",
							"categoria": req.body[index].categoria,
							"isgenerico":req.body[index].generico,
							"id":1
							};
					var azureRequest =  unirest.post("http://"+SAPO_HOST+"/openshiftproject/rest/productos/create?mongo=false")
						.type('json')
						//.headers({"Ocp-Apim-Trace":"true","Ocp-Apim-Subscription-Key":"9f86432ae415401db0383f63ce64c4fe"})
						.send(dataproducto)
						.end(function(response){
							console.log("Producto agregado a la base Postgresql.");
							console.log("ID producto: "+response.body.id);
							//ajusto id rdbms
							response1.body.rdbms_id=response.body.id;
							response1.body.rdbms_producto=response.body;

							//mongo
							var MongoClient = require('mongodb').MongoClient; 

							MongoClient.connect(url, {native_parser:true}, function(err, db) {
							    assert.equal(null, err);
							    db.collection('test').insert(response1.body, function(err, result) {
							      console.log(result.ops[0]._id);
							      db.close();
							    });
							});


						});

			      }
			);
	};
	var list = [];
	for(i=0; i<req.body.length; i++){
		try{
		   dorequest(i);	
		   list.push({"id":req.body[i].id, "status":"success"}); 
		}catch(err){
		   list.push({"id":req.body[i].id, "status":"error"});   	
		}

	}
	res.send(list);
}
);

//Funciones únicas para administrador.
/************************************************/
/*		Templates			*/
/************************************************/

app.get('/algoritmos/templates', function(req,res){
var umbral = req.query.umbral || 2;
console.log(umbral);
var Request = unirest.get('http://'+SAPO_HOST+':8080/openshiftproject/rest/almacenes')
		.type('json')
		.end(function (response) {

			var almacenes = response.body;
			var almacenes_sense = {};
			var categorias = {};
			for(i=0;i<almacenes.length; i++){
				var group_cat =almacenes[i].categorias; 
				if(!(group_cat in almacenes_sense)){ 
					almacenes_sense[group_cat] = {"count":0,"categorias": group_cat };
				}
				almacenes_sense[group_cat].count = almacenes_sense[group_cat].count+1;
					
				for(c=0;c<almacenes[i].categorias.length;c++){
					//console.log(cat);
					var cat = almacenes[i].categorias[c];
					if(!(cat.id in categorias)){ categorias[cat.id] = 0 }	
					categorias[cat.id]= categorias[cat.id]+1;
				}
			}

			var array = [];
			for(var c in almacenes_sense){
				if(almacenes_sense[c].categorias.length>0 && almacenes_sense[c].count>=umbral)  //almacenes vacios
				array.push(almacenes_sense[c]);
			}
			array.sort(function(a,b){
			return(b.count-a.count);
			});

			//RESPUESTA
			res.send({"categorias_count": categorias, "cat_group": array});

			//Agrego a la base mongo la sugerencia
			var MongoClient = require('mongodb').MongoClient; 
			var templateid;
			MongoClient.connect(url, {native_parser:true}, function(err, db) {
			    assert.equal(null, err);
			    db.collection('templatenotifications')
			    .insert({ "fecha": (new Date()).toJSON(), "categorias": array}, function(err, result) {
				    templateid = result.ops[0]._id;
				    //envío al websocket
				    ws.send('Nuevo template recomendado! '+ templateid);
				    db.close();
				    }
			    );
			});
	
		});

});


app.get('/algoritmos/wordnet/:n', function(req,res){

var wn = wordNet("/home/leonardo/Downloads/sqlite-31.db", false);
//var word = new wn.Word("cat");
wn.fetchSynset(req.params.n+".n.1").then(function(synset){
    console.log(synset)
    synset.getDomains().then(function(domain){
	//console.log('######');
	//console.log(domain);
        ///console.log(util.inspect(domain, null, 3))
    });
});


wn.fetchSynset(req.params.n+".n.1").then(function(synset){
    synset.getHypernyms().then(function(hypernym){
	console.log('####hiperonimo')
        console.log(hypernym);
	for(i=0;i<hypernym.length;i++){
	   console.log(hypernym[i].words);
	}
    });
});

var kiss = new wn.Word("kiss","n");
kiss.getSynsets(function(err, data){
    console.log("sinónimos");
    console.log(data);
});


res.send("");
/*
var Request = unirest.get('http://'+SAPO_HOST+':8080/openshiftproject/rest/categorias?genericas=false')
		.type('json')
		.end(function (response) {
	var categorias = response.body;
	for(i=0; i<categorias.length;i++){
		
	}

});

*/
});



app.get('/algoritmos/categorias', function(req,res){
	var Request = unirest.get('http://'+SAPO_HOST+'/openshiftproject/rest/algoritmos/categorias')
		.type('json')
		.end(function (response) {

			var natural = require('natural');
			var classifier = new natural.BayesClassifier();


			var cats = response.body;
			for( i=0; i< cats.length; i++ ){
			  console.log(cats[i].categoriaid);
			  var prods = cats[i].productos;
			  for( j=0; j<prods.length; j++){
			    console.log(prods[j].descripcion);
			    if(prods[j].descripcion!=null)
			    	classifier.addDocument(prods[j].descripcion, cats[i].categoriaid);
			    if(prods[j].nombre!=null)
			    	classifier.addDocument(prods[j].nombre, cats[i].categoriaid);
			  }
			}
			classifier.train();
			console.log(classifier.getClassifications('Sebastián'));
		});



/*
	classifier.addDocument('my unit-tests failed.', 'software');
	classifier.addDocument('tried the program, but it was buggy.', 'software');
	classifier.addDocument('the drive has a 2TB capacity.', 'hardware');
	classifier.addDocument('i need a new power supply.', 'hardware');

	classifier.train();

	console.log(classifier.classify('did the tests pass?'));
	console.log(classifier.classify('did you buy a new drive?'));*/
}
);


app.get('/client', function(req,res){
	console.log('sticky');
	io.of('/templates').emit('receive', 'server this..');
	res.send('ok');
});





//listener
var server_port = process.env.OPENSHIFT_NODEJS_PORT || 3000;
var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1';
 

var server = app.listen(server_port, server_ip_address, function () {
  console.log( "Listening on " + server_ip_address + ", server_port " + server_port )
});


/*Websocket Server+ REDIS = 11*/
var io = require('socket.io')(server);
var host =  'pub-redis-14394.us-east-1-4.5.ec2.garantiadata.com';
var port = 14394;
var redis = require('redis').createClient;
var adapter = require('socket.io-redis');
var pub = redis(port, host, { auth_pass: "kraken" });
var sub = redis(port, host, { detect_buffers: true, auth_pass: "kraken" });
io.adapter(adapter({ pubClient: pub, subClient: sub }));

/*WEBSOCKETS DEFINITION ENDPOINTS*/

//CHAT
io.of('/sapochat')
.on('connection', function (socket) {
	//join almacen
	socket.on('adduser', function(username, almacen){
		console.log('user joined '+username);
		socket.username = username;
		socket.room = almacen;
		socket.join(almacen);
	});
	//envio mensaje
	socket.on('sendchat', function (data) {
		io.of('/sapochat').in(socket.room).emit('receivechat', socket.username, data);
		console.log(data);
	});

	//
	socket.on('disconnect', function(){
		console.log(socket.username+' disconnected');
		socket.leave(socket.room);
	});
});

//TEMPLATES
io.of('/templates')
.on('connection', function (socket) {
	socket.on('sendnotification', function (data) {
		socket.broadcast.emit('receive', data);
		console.log(data);
	});
	socket.on('disconnect', function(){
		console.log('disconnect');
	});
});

io.of('/productos')
.on('connection', function (socket) {
	socket.on('sendnotification', function (data) {
		socket.broadcast.emit('receive', data);
		console.log(data);
	});
	socket.on('disconnect', function(){
		console.log('disconnect');
	});
});


