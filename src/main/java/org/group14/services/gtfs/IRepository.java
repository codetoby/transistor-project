package org.group14.services.gtfs;

import java.util.List;

public interface IRepository<T extends GTFSModel> {
    T getById(String id);
    List<T> getAll();
}
