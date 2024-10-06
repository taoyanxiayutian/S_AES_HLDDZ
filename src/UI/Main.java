package UI;

import functionalClass.BruteForceCracker;

public class Main {
	public static void main(String []args)
	{
		mainwindow SEDS=new mainwindow();
		SEDS.displayWindow();
		PoliceListenEncrypt policeEncrypt=new PoliceListenEncrypt();
		PoliceListenDecrypt policeDecrypt=new PoliceListenDecrypt();
		ClearAllListen clPoliceEncrypt=new ClearAllListen();
		ClearAllListen clPoliceDecrypt=new ClearAllListen();
		SEDS.setBruteForceListener(new BruteForceCracker());

		SEDS.setMyCommandListener(policeEncrypt);
		SEDS.setMyCommandListenerB(policeDecrypt);
		SEDS.setMyCommandListenerClear(clPoliceEncrypt);
		SEDS.setMyCommandListenerClearD(clPoliceDecrypt);

	}
}
