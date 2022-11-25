package com.lwt.realestatemanager.utils;

import android.annotation.SuppressLint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"}) // Can't edit for the "SOUTENANCE"
public class Utils {

	/**
	 * Convert price of an estate (Dollars to Euros)
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 */
	public static int convertDollarToEuro(int dollars) {
		return (int) Math.round(dollars * 0.8467188);
	}

	/**
	 * Convert price of an estate (Euros to Dollars)
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 */
	public static int convertEuroToDollar(int euros) {
		return (int) Math.round(euros * 1.18103);
	}

	/**
	 * Convert today date in a more apropriate format
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 */
	public static String getTodayDate() {
		@SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(new Date());
	}

	/**
	 * Check that you have internet access
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 */
	public static boolean isInternetAvailable() {
		InetAddress address = null;
		try {
			address = InetAddress.getByName("www.google.com");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (address == null)
			return false;

		return !address.toString().equals("");
	}
}