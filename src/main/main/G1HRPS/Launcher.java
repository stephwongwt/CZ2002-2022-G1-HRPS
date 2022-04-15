package main.G1HRPS;

public class Launcher {
    public static void main(String[] args) {
        AppManager appmanager = new AppManager();
        appmanager.Initialize();
        appmanager.Run();
        System.exit(0);
    }
}