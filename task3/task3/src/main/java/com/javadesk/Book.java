package com.javadesk;

import java.util.List;
import java.io.Serializable;

enum Location
{
    BOOKCASE("Книжный шкаф"),
    DESK("Рабочий стол"),
    ARCHIVE("Архив"),
    UNEXPECTED("Место хранения не определено");


    private String message;
    Location(String mes)
    {
        this.message = mes;
    }

    public String getMessage()
    {
        return message;
    }
}

public class Book implements Serializable{
    private List<String> authors; 
    private String name;
    private String description;
    private int pageCount;
    private Location storage;

    public Book() {}
    public Book(String name, List<String> autors, Location storage)
    {
        this.name = name;
        this.authors = autors;
        this.storage = storage;
    }
    public Book(String name, List<String> autors, Location storage, String description, int pageCount)
    {
        this.name = name;
        this.authors = autors;
        this.storage = storage;
        this.description = description;
        this.pageCount = pageCount;
    }

    //==========SETTERS==========
    public void setName(String newName)
    {
        this.name = newName;
    }
    public void setAutors(List<String> newAutors)
    {
        this.authors = newAutors;
    }
    public void setStorage(Location newStorage)
    {
        this.storage = newStorage;
    }
    public void setDescription(String newDescription)
    {
        this.description = newDescription;
    }
    public void setPageCount(int newCount)
    {
        this.pageCount = newCount;
    } 

    //==========GETTERS=========
    public String getName()
    {
        return this.name;
    }
    public String getDescription()
    {
        return this.description;
    }
    public Location getStorage()
    {
        return this.storage;
    }
    public List<String> getAutors()
    {
        return this.authors;
    }
    public int getPageCount()
    {
        return this.pageCount;
    }

    public String toString(int i)
    {
        return String.format("%d. Книга: %s | Авторы: %s | Количество страниц: %d | Место: %s", i, name, authors.toString(), pageCount, storage.getMessage());
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty()
            && authors != null && !authors.isEmpty()
            && storage != null;
    }
}
