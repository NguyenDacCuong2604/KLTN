import ml.ClassDFVectorizer;
import ml.Document;
import ml.IDFVector;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DataUtils {
//    static public void exportClassDFVector(ClassDFVectorizer dfVector, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
//        IDFVector vector = dfVector.calculateDFPercent();
//        vector.sortAscending();
//        DataUtils.exportIdfVector(vector, fileName);
//    }

    static public void exportIdfVector(IDFVector idfVector, String normalizeFile) throws FileNotFoundException, UnsupportedEncodingException, FileNotFoundException, UnsupportedEncodingException {
        int count = idfVector.getNormalizedVectorSize();
        PrintWriter outFile = new PrintWriter(normalizeFile, "UTF-8");
        outFile.println("Vetor Dimension: " + count );
        for(int i=0; i<count; i++) {
            outFile.println(idfVector.getNormalizedTerm(i) + "\t" + idfVector.getNormalizedTermIDF(i));
        }
        outFile.close();
    }

    static public void exportIdfVector(IDFVector idfVector, int count, String normalizeFile) throws FileNotFoundException, UnsupportedEncodingException {
        if (count>idfVector.getNormalizedVectorSize()) count = idfVector.getNormalizedVectorSize();
        PrintWriter outFile = new PrintWriter(normalizeFile, "UTF-8");
        outFile.println("Vetor Dimension: " + count );
        for(int i=0; i<count; i++) {
            outFile.println(idfVector.getNormalizedTerm(i) + "\t" + idfVector.getNormalizedTermIDF(i));
        }
        outFile.close();
    }

    static public void exportLibSVMTrainData(List<Document> documents, IDFVector idfVector, String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        String sep = " ";
        String label;
        double[] vector;

        PrintWriter file = new PrintWriter(fileName, charset);
        for(Document doc: documents) {
            label = doc.getLabelID() + "";
            vector = doc.getNormalizedTFIDFVector(idfVector);
            StringBuilder sb = new StringBuilder(label);
            for (int i = 0; i<vector.length; i++) {
                if (vector[i]!=0.0) {
                    sb.append(sep + (i+1) + ":" + vector[i]);
                }
            }
            file.println(sb.toString());
        }
        file.close();
    }

    static public void exportLibSVMTrainDataCVS(List<Document> documents, IDFVector idfVector, String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        String sep = ",";
        String label;
        double[] vector;

        PrintWriter file = new PrintWriter(fileName, charset);

        int count = idfVector.getNormalizedVectorSize();
        StringBuilder sb = new StringBuilder("Label");
        for (int i = 0; i<count; i++) {
            sb.append(sep + "value" +(i+1));
        }
        file.println(sb.toString());

        for(Document doc: documents) {
            label = doc.getLabelID() + "";
            vector = doc.getNormalizedTFIDFVector(idfVector);
            sb = new StringBuilder(label);
            for (int i = 0; i<count; i++) {
                sb.append(sep + vector[i]);
            }
            file.println(sb.toString());
        }
        file.close();
    }
    static public void exportLibSVMTrainDataCVS_stdLabel(List<Document> documents, IDFVector idfVector, String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        String sep = ",";
        double[] vector;

        PrintWriter file = new PrintWriter(fileName, charset);

        int count = idfVector.getNormalizedVectorSize();
        StringBuilder sb = new StringBuilder("Label");
        for (int i = 0; i<count; i++) {
            sb.append(sep + "value" +(i+1));
        }
        file.println(sb.toString());
        int stdLabel = -1;
        int label;
        int oldLabel = -1;
        for(Document doc: documents) {
            label = doc.getLabelID();
            if (label!=oldLabel) {
                oldLabel = label;
                stdLabel++;
            }
            vector = doc.getNormalizedTFIDFVector(idfVector);
            sb = new StringBuilder(stdLabel + "");
            for (int i = 0; i<count; i++) {
                sb.append(sep + vector[i]);
            }
            file.println(sb.toString());
        }
        file.close();
    }

    static public void exportLibSVMTrainData_stdLabel(List<Document> documents, IDFVector idfVector, String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        String sep = " ";
        double[] vector;
        int stdLabel = -1;
        int label;
        int oldLabel = -1;

        PrintWriter file = new PrintWriter(fileName, charset);
        for(Document doc: documents) {
            label = doc.getLabelID();
            if (label!=oldLabel) {
                oldLabel = label;
                stdLabel++;
            }

            vector = doc.getNormalizedTFIDFVector(idfVector);
            StringBuilder sb = new StringBuilder(stdLabel+ "");
            for (int i = 0; i<vector.length; i++) {
                if (vector[i]!=0.0) {
                    sb.append(sep + (i+1) + ":" + vector[i]);
                }
            }
            file.println(sb.toString());
        }
        file.close();
    }

}
