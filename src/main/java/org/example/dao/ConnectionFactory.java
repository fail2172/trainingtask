package org.example.dao;

import org.hibernate.Session;

public interface ConnectionFactory {
    Session openSession();
}
