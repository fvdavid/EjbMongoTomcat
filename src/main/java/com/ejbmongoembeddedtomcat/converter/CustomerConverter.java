/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejbmongoembeddedtomcat.converter;

import com.ejbmongoembeddedtomcat.entity.Customer;
import org.bson.types.ObjectId;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
/**
 *
 * @author saddamtbg
 */
public class CustomerConverter {
    
    public static DBObject toDBObject(Customer cus) {
        
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("name", cus.getName())
                .append("address", cus.getAddress())
                .append("kota", cus.getKota());
        
        if(cus.getId() != null) {
            builder = builder.append("_id", new ObjectId(cus.getId()));
        }
        
        return builder.get();
    }
    
    public static Customer toCustomer(DBObject doc) {
        Customer cus = new Customer();
        cus.setName((String) doc.get("name"));
        cus.setAddress((String) doc.get("address"));
        cus.setKota((String) doc.get("kota"));
        ObjectId id = (ObjectId) doc.get("_id");
        cus.setId(id.toString());
        
        return cus;
    }
}
