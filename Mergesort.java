import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Dateiname    : Mergesort.java
 * Beschreibung : Einsendeaufgabe 2 für den Kurs Algorithmen
 *
 * @author Tobias Wagner (s79080@beuth-hochschule.de)
 * @version 1.00, 02.12.2019
 */

// Klasse Mergesort zum Sortieren von Arrays und zum Testen der gegebenen Dateien Rand... und Sort...
// Jeweils mit Anfangs- und Endzeit
public class Mergesort
{

	private static String[] testDaten = {"Rand10_1", "Rand10_2", "Rand20_1", "Rand20_2",
			"Rand50_1", "Rand50_2", "Rand100_1", "Rand100_2",
			"Rand200_1", "Rand200_2", "Rand500_1", "Rand500_1",
			"Rand1000_1", "Rand1000_2", "Rand10000_1", "Rand10000_2",
			"Rand100000_1", "Rand100000_2", "Sort10_1", "Sort20_1", "Sort50_1", "Sort100_1", "Sort200_1",
			"Sort500_1", "Sort1000_1", "Sort10000_1", "Sort100000_1"};

	//Deklaration der main Methode
	public static void main(String[] args) throws IOException
	{
		ArrayList<TestErgebnis> ergebnisListe = new ArrayList<>();

		for (String testFilename : testDaten) {
			// Einlesen des zu bearbeitenden Arrays - Auswahl der Datei mit dem Array
			int[] testDatensatz = FileIntArray.FileToIntArray(testFilename);

			// Ausgeben des unsortierten Arrays
			System.out.println(Arrays.toString(testDatensatz));

			// Erfassen der Anfangszeit
			double anfangszeit = System.nanoTime();

			// Ausführen von mergeSort
			mergeSort(testDatensatz, 0, testDatensatz.length - 1);

			// Erfassen der Endzeit
			double endzeit = System.nanoTime();

			// Ausgabe des sortierten Arrays
			System.out.println(Arrays.toString(testDatensatz));

			// Ausgabe der benötigten Dauer für den Durchlauf von mergeSort
			double dauer = endzeit - anfangszeit;
			System.out.println("Die benötigte Zeit betraegt: " + dauer + " Nanosekunden");

			// Speichern des Testergebnisses in unsere ergebnissliste
			TestErgebnis testErgebnis = new TestErgebnis(testFilename, dauer);
			ergebnisListe.add(testErgebnis);
		}

		File datei = new File("Testlaufergebnis.csv");
		FileWriter schreiber = new FileWriter(datei);

		// CSV Kopzeile
		schreiber.write("Testlauf;Zeit\n");

		for (TestErgebnis testErg : ergebnisListe) {
			schreiber.write(testErg.getTestDataname() + ";" + testErg.getTime() + "\n");
		}
		schreiber.close();
	}

	// Rekursive Funktion Mergesort
	// Initialisierung Mergesort mit dem eingelesenen Array, dem linken Index, dem rechten Index
	private static void mergeSort(int[] datensatz, int indexLinks, int indexRechts)
	{
		if (indexLinks < indexRechts) {
			int mitte = (indexLinks + indexRechts) / 2;
			mergeSort(datensatz, indexLinks, mitte);
			mergeSort(datensatz, mitte + 1, indexRechts);
			merge(datensatz, indexLinks, mitte, indexRechts);
		}
	}

	//Merge - Methode, die in Mergesort aufgerufen wird
	private static void merge(int[] datensatz, int l, int m, int r)
	{
		// H = Hilfsarray, zum Zwischenspeichern, mit der Länge des Arrays A
		int hilfsArray[] = new int[datensatz.length];

		// definiere i = linker Index, solange i kleiner gleich rechter Index, führe aus und erhöhe i
		for (int i = l; i <= r; i++) {
			// Zwischenspeichern von Array datensatz[i] nach hilfsarray[i]
			hilfsArray[i] = datensatz[i];
		}
		// Laufzeitvariablen i, j und k für die while - Schleifendurchläufe
		int i = l;
		int j = m + 1;
		int k = l;
		// solange i kleiner gleich mittlerer Index UND j kleiner gleich rechter Index,
		while (i <= m && j <= r) {

			// Kopiere kleineren aktuellen Wert hilfsArray[j] -> datensatz[k] bzw. hilfsArray[i] -> datensatz[k]
			// Erhöhe aktuellen Index von Teilarray mit kleinerem Wert
			// Erhöhe aktuellen Index von H

			// Wenn H an der Stelle i kleiner gleich H an der Stelle J, dann setze H[i] auf A[k] erhöhe i um eins
			if (hilfsArray[i] <= hilfsArray[j]) {
				datensatz[k] = hilfsArray[i];
				i++;
				// ansonsten setze H an der Stelle j auf A[k] und erhöhe j um 1
			} else {
				datensatz[k] = hilfsArray[j];
				j++;
			}
			// Erhöhe k, die Zählvariable des Arrays A um eins,
			// um die nächste Position im Array A im kommenden Durchlauf einzufügen
			k++;
		}

		// übertrage Werte des noch nicht vollständig durchlaufenen Teilarrays nach H
		// Kopiere H nach A (von Index l bis Index r)
		while (i <= m) {
			datensatz[k] = hilfsArray[i];
			k++;
			i++;
		}

		while (j <= r) {
			datensatz[k] = hilfsArray[j];
			k++;
			j++;

		}
	}


	// Hilfsklasse zum speichern von Testergebnissen
	private static class TestErgebnis
	{
		private final String testDataname;
		private final Double time;

		public TestErgebnis(String testDataname, Double time)
		{
			this.testDataname = testDataname;
			this.time = time;
		}

		public String getTestDataname()
		{
			return testDataname;
		}

		public Double getTime()
		{
			return time;
		}
	}
}
