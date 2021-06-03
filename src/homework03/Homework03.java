//консольный файловый менеджер
package homework03;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Homework03 {

    public static void main(String[] args) throws IOException {

        File directory = new File("files");
    
        Scanner in = new Scanner(System.in);
        String command = "";
        while(true){
            System.out.println("Введите команду (list | view <имя файла> | copy <имя исходного файла> <имя конечного файла> | exit)");
            System.out.print(">");
            if(in.hasNextLine()){
                command = in.nextLine();
                String commandType = "";
                String[] commandArgs = {};
                List commandWords = commandParse(command);
                if(commandWords.size() > 0){
                    commandType = (String)commandWords.get(0);
                    commandArgs = (String[])commandWords.get(1);
                    if ("exit".equals(commandType)){
                        break;
                    }else{
                        switch (commandType){
                            case("list"):
                                list(directory);
                                break;
                            case("view"):
                                view(directory, commandArgs);
                                break;
                            case("copy"):
                                copy(directory, commandArgs);
                                break;
                        }
                    }
                }else{
                    System.out.println("Команда некорректна");
                }
            }else{
                in.next();
            }
        }
    }
    
    private static List commandParse (String command){
        List result = new ArrayList();
        String[] commandWords = command.split(" ");
        String[] emptyArray = {};
        Set<String> correctCommandsArgs0 = new HashSet<String>(Arrays.asList("list","exit"));
        Set<String> correctCommandsArgs1 = new HashSet<String>(Arrays.asList("view"));
        Set<String> correctCommandsArgs2 = new HashSet<String>(Arrays.asList("copy"));
        int commandWordsLength = commandWords.length;
        if(commandWordsLength > 0){
            String[] commandArgs = new String[2];
            if( correctCommandsArgs0.contains(commandWords[0]) && commandWordsLength==1 ){
                result.add(commandWords[0]);
                result.add(emptyArray);
            }else if( correctCommandsArgs1.contains(commandWords[0]) && commandWordsLength==2 ){
                result.add(commandWords[0]);
                commandArgs[0] = commandWords[1];
                result.add(commandArgs);
            }else if( correctCommandsArgs2.contains(commandWords[0]) && commandWordsLength==3 ){
                result.add(commandWords[0]);
                commandArgs[0] = commandWords[1];
                commandArgs[1] = commandWords[2];
                result.add(commandArgs);
            }
        }
        return result;
    }
    
    private static void list(File directory){
        File[] files = directory.listFiles();
        for (File elem: files){
            System.out.println(elem.getName());
        }
        System.out.println("Команда выполнена успешно");
    }
    
    private static void view(File directory, String[] commandArgs) {
        File file = new File(directory.getPath()+"\\"+commandArgs[0]);
        try(Scanner in = new Scanner(new FileReader(file.getPath()))){
            while(true){
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    System.out.println(line);
                }else{
                    break;
                }
            }
            System.out.println("Команда выполнена успешно");
        }catch(FileNotFoundException e){
            System.out.println("Файл " + file.getPath() + " не найден");
        }
    }

    private static void copy(File directory, String[] commandArgs) throws IOException {
        File fileSource = new File(directory.getName()+"\\"+commandArgs[0]);
        File fileDest = new File(directory.getName()+"\\"+commandArgs[1]);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileSource);
            fos = new FileOutputStream(fileDest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("Команда выполнена успешно");
        }catch(FileNotFoundException e){
            System.out.println("Файл " + fileSource.getPath() + " не найден");
        }catch(IOException e){
            System.out.println("Ошибка ввода/вывода");
        }finally {
            fis.close();
            fos.close();
        }
    }

}
