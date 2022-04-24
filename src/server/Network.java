package server;
import java.util.ArrayList;
import java.io.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

import java.io.*;
import java.net.*;
import java.util.*;
public abstract class Network extends Thread{
    public ArrayList<ClientThread> threads=new ArrayList<>();
    public static final List<ObjectOutputStream> clientsA = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
}