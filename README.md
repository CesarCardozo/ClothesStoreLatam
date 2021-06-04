# ClothesStoreLATAM

Prueba tecnica para Experimentality, por Cesar Cardozo

## About

Api para compañia emergente llamada Clothesstore LATAM situada en Colombia dedicada a la
venta de productos a través de internet.

## Api Endpoints

Los endpoints desarrollados se encuentran en heroku, al igual que la [BD](https://clothesstorelatam.herokuapp.com/h2) (JDBC: 'jdbc:h2:mem:testdb' ; user: 'sa'; password: '')

* Lista de productos mas buscados:  
Endpoint: https://clothesstorelatam.herokuapp.com/clothItems/moreSearchedFor  
RequestMethod: GET  
Optional params: paginationSize (numeric), currentPage(numeric)  
    
* Busqueda de productos por nombre:    
Endpoint: https://clothesstorelatam.herokuapp.com/clothItems  
RequestMethod: GET  
Params: keywords (string)  
Optional params: paginationSize (numeric), currentPage(numeric)  
    
* Detalle de producto  
Endpoint: https://clothesstorelatam.herokuapp.com/clothItems  
Requestethod: GET  
Params: idClothItem (numeric)  
    
* Crear producto nuevo  
Endpoint: https://clothesstorelatam.herokuapp.com/clothItems  
RequesMethod: POST  
Params:    
clothItem (object):{  
                "clothItemName"(string),  
                "clothItemDescription"(string),  
                "clothItemPrice"(numeric),  
                "clothItemDiscount":(numeric),  
                "clothItemImageUrls"[(string)]  
             }  
             
* Agregar producto a carrito de compras  
Endpoint: https://clothesstorelatam.herokuapp.com/addToCart  
RequestMethod: GET  
Params: idClothItem(numeric)  
OptionalParams: idShoppingCar(numeric)  
    
* Consultar estado de carrito de compras  
  Endpoint: https://clothesstorelatam.herokuapp.com/shoppingCart  
    RequestMethod: GET  
    Params: idShopingCart(numeric)  
    
* Subir imagen de producto a contenedor  
  Endpoint: https://clothesstorelatam.herokuapp.com/storage/uploadImage  
    RequesMethod: POST  
    Params: file(file)  
    
## Api Test Collection on postman

The API's test collection for postman can be downloaded [here](https://github.com/CesarCardozo/ClothesstoreLATAM/blob/development/Experimentality.postman_collection.json)
