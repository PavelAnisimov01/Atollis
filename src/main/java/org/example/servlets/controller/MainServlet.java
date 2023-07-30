package org.example.servlets.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import static org.example.servlets.repository.JDBCExample.*;

@WebServlet("/api")
public class MainServlet extends HttpServlet {
    private static final Logger logger= LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("GetRequest start");
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        String json;
        try {
            json = getRegion();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        out.println(json);
        logger.debug("GetRequest finished");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("PostRequest start");
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
        JSONObject json = new JSONObject(jsonString);
        logger.debug("This is json from request"+json);
        String name = json.getString("name");
        String administrativeRegion = json.getString("administrativeRegion");
        Integer population = json.getInt("population");
        Double area = json.getDouble("area");
        logger.debug("It is the data that will be entered in the table: name ="+name+",administrativeRegion = "+administrativeRegion+", population ="+population+", area"+ area);
        createRegion(name, administrativeRegion, population, area);
        logger.debug("PostRequest finished");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("PutRequest start");
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
        JSONObject json = new JSONObject(jsonString);
        logger.debug("This is json from request"+json);
        String name = json.getString("name");
        String administrativeRegion = json.getString("administrativeRegion");
        Integer population = json.getInt("population");
        Double area = json.getDouble("area");
        logger.debug("It is the data that will be need to update the table: name ="+name+",administrativeRegion = "+administrativeRegion+", population ="+population+", area"+ area);
        try {
            updateRegion(name,administrativeRegion,population,area);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        logger.debug("PutRequest finished");
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("DeleteRequest start");
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
        JSONObject json = new JSONObject(jsonString);
        logger.debug("This is json from request"+json);
        String name = json.getString("name");
        String administrativeRegion = json.getString("administrativeRegion");
        logger.debug("This is the data on which it will be located adminUnit to delete from the table: name ="+name+",administrativeRegion = "+administrativeRegion);
        try {
            deleteRegion(name,administrativeRegion);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        logger.debug("DeleteRequest finished");
    }
}