/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejbmongoembeddedtomcat.service;

import com.ejbmongoembeddedtomcat.converter.CustomerConverter;
import com.ejbmongoembeddedtomcat.entity.Customer;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
/**
 *
 * @author saddamtbg
 */
public class MongoService {
    
    private final DBCollection col;
    
    public MongoService(MongoClient mongo) {
        this.col = mongo.getDB("eshopper").getCollection("Customers");
    }
    
    public Customer createCustomer(Customer cus) {
        
        DBObject doc = CustomerConverter.toDBObject(cus);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        cus.setId(id.toString());
        return cus;
    }
    
    public void updateCustomer(Customer cus) {
        
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(cus.getId())).get();
        this.col.update(query, CustomerConverter.toDBObject(cus));
    }
    
    public List<Customer> readAllCustomer() {
        
        List<Customer> customers = new ArrayList<Customer>();
        DBCursor cursor = col.find();
        while(cursor.hasNext()) {
            DBObject doc = cursor.next();
            Customer cus = CustomerConverter.toCustomer(doc);
            customers.add(cus);
        }
        return customers;
    }
    
    public void deleteCustomer(Customer cus) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(cus.getId())).get();
        this.col.remove(query);
    }
    
    public Customer readCustomer(Customer cus) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(cus.getId())).get();
        DBObject data = this.col.findOne(query);
        return CustomerConverter.toCustomer(data);
        
    }
}
