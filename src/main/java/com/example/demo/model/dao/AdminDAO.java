package com.example.demo.model.dao;

import com.example.demo.model.entities.Admin;

import java.util.List;

public interface AdminDAO {
    void addAdmin(Admin admin);
    Admin getAdminById(int id);
    List<Admin> getAllAdmins();
    void updateAdmin(Admin admin);
    void deleteAdmin(int id);
}
