package Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Delivery {
    @Id
    @GeneratedValue
    public int id;
    public String Name;
    public String Description;
    public String Location;
    public Date Date;
    public String Delivery_person;
    public String OrderId;
    public String Client_Id;

    public Delivery(int id, String name, String description, String location, Date date, String delivery_person, String orderId, String client_Id) {
        this.id = id;
        Name = name;
        Description = description;
        Location = location;
        Date = date;
        Delivery_person = delivery_person;
        OrderId = orderId;
        Client_Id = client_Id;
    }

    public Delivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getDelivery_person() {
        return Delivery_person;
    }

    public void setDelivery_person(String delivery_person) {
        Delivery_person = delivery_person;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }
}
