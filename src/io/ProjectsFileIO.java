package io;

import model.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hints on how to implement serialization and deserialization
 * of lists of projects and users.
 */
public class ProjectsFileIO {

    /**
     * Call this method before the application exits, to store the users and projects,
     * in serialized form.
     */
    public static void serializeToFile(File file, List<Project> data) throws IOException {
//        try
//        {

        //Saving of object in a file
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        // Serialize the list of projects
//            out.writeInt(data.size());
        data.forEach(project -> {
            try {
                out.writeObject(project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        out.close();
        fileOut.close();

        System.out.println("File has been serialized");
//        }
//
//        catch(IOException ex)
//        {
//            System.out.println("IOException is caught");
//        }

    }

    /**
     * Call this method at startup of the application, to deserialize the users and
     * from file the specified file.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Project> deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        // Reading the object from a file
        FileInputStream fileInput = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileInput);

        // Deserialize the list of projects
        ArrayList<Project> projects = new ArrayList<>();
        Project project;
        try{
            while((project = (Project) in.readObject()) != null) {
                projects.add(project);
            }
        } catch (EOFException e) {
            // Do nothing - we have reached the end of the file
        }
        in.close();
        fileInput.close();

        System.out.println("File has been deserialized ");
        return projects;
    }

    private ProjectsFileIO() {}
}
