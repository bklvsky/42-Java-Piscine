import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Executor {

    private Path cwd;

    private static Executor instance = null;

    private Executable[] executors;

    private Executor() {
        this.cwd = Paths.get("").toAbsolutePath();
        executors = new Executable[3];
        executors[0] = new Cd();
        executors[1] = new Ls();
        executors[2] = new Mv();
    }

    private Path makePath(String path) {
        if (!Paths.get(path).isAbsolute())
            return Paths.get(cwd + "/" + path);
        return Paths.get(path);
    }

    public void setCWD(String path) {
        this.cwd = Paths.get(path).toAbsolutePath().normalize();
    }

    public void execute (Command command) throws IOException {
        if (command.getType() < 0) {
            System.err.println("Error: not a command");
            return;
        }
        instance.executors[command.getType()].execute(command.getArgs());
    }

    public static Executor getInstance() {
        if (instance == null) {
            instance = new Executor();
        }
        return instance;
    }

    private class Ls implements Executable{

        public void execute (String [] args) throws IOException {
            if (args.length != 0) {
                System.out.println("Error: ls command doesn't take arguments");
                return;
            }
            String[] files = Files.list(cwd).map(Path::toString).toArray(String[]::new);
            for (String file: files) {
                System.out.println(
                        Paths.get(file).getFileName()
                        + " "
                        + makeSize(Paths.get(file))
                );
            }
        }

        private String makeSize(Path file) throws IOException{
            long size = Files.size(file);
            if (size > 1024) {
                size /= 1024;
                if (size > 1024) {
                    return (size + " MB");
                }
                return (size + " KB");
            }
            return (size + " B");
        }
    }

    private class Mv implements Executable {
        private static final int FILE_TO_MOVE = 0;
        private static final int TARGET = 1;

        public void execute (String [] args) throws IOException {
            if (args.length != 2) {
                System.out.println("Error: mv command takes two arguments");
                System.out.println("Usage: mv old_path new_path");
                return;
            }
            Path file_to_move = makePath(args[FILE_TO_MOVE]);
            Path target = makePath(args[TARGET]);
            if (Files.isDirectory(target)) {
                target = makePath(args[TARGET] + "/" + args[FILE_TO_MOVE]);
            }
            Files.move(file_to_move, target);
        }
    }
    
    private class Cd implements Executable {

        private static final int NEW_PATH = 0;

        public void execute (String [] args) throws IOException {
            if (args.length != 1) {
                System.out.println("Error: cd command takes one arguments");
                System.out.println("Usage: cd new_path");
                return;
            }
            Path newPath = makePath(args[NEW_PATH]);

            if (!Files.isDirectory(newPath)) {
                throw new NotDirectoryException(args[NEW_PATH]
                                                + ": not a directory");
            } else if (!Files.isReadable(newPath)) {
                throw new AccessDeniedException(args[NEW_PATH] 
                                                + ": access denied");
            } else {
                if (newPath.isAbsolute()) {
                    setCWD(newPath.toString());
                } else {
                    setCWD(cwd.toString() + "/" + newPath.toString());
                }
                System.out.println(cwd);
            }
        }
    }
}
