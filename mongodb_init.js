db = db.getSiblingDB('web_flux');

db.createCollection("products");

db.products.insert({"_id": "100", "name": "Azúcar", "category": "Alimentación", "price": 1.1, "stock": 20});
db.products.insert({"_id": "101", "name": "Leche", "category": "Alimentación", "price": 1.2, "stock": 15});
db.products.insert({"_id": "102", "name": "Jabón", "category": "Limpieza", "price": 0.89, "stock": 30});
db.products.insert({"_id": "103", "name": "Mesa", "category": "Hogar", "price": 125.0, "stock": 4});
db.products.insert({"_id": "104", "name": "Televisión", "category": "Hogar", "price": 650.0, "stock": 10});
db.products.insert({"_id": "105", "name": "Huevos", "category": "Alimentación", "price": 2.2, "stock": 30});
db.products.insert({"_id": "106", "name": "Fregona", "category": "Limpieza", "price": 3.4, "stock": 6});
db.products.insert({"_id": "107", "name": "Detergente", "category": "Limpieza", "price": 8.7, "stock": 12});