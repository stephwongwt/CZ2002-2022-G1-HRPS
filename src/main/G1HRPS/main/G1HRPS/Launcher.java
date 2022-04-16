package main.G1HRPS;

/**
 * Launcher to call upon the application manager.
 * Ensures application quits properly.
 * 
 * @author Steph Wong
 *
 */
public class Launcher {

	/**
	 * Main application to be run.
	 * 
	 * @param args command line arguments provided.
	 */
	public static void main(String[] args) {
		AppManager appmanager = new AppManager();
		appmanager.Initialize();
		appmanager.Run();
		System.exit(0);
	}
}