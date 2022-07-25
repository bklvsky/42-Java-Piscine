import java.util.Scanner;

public class Program {
    
    private static final int DAYS_A_WEEK = 7;
    private static final int DAYS_SEPT = 30;
    private static final int MAX_LES_A_WEEK = 10;
    private static final int MAX_LES_A_MONTH = 50;
    private static final int MAX_STUDENTS = 10;
    private static final int MAX_COLUMNS = 10;

    private static final int NO_DATA = 0;

    private static final int IN_TIME = 0;
    private static final int IN_WEEKDAY = 1;

    private static final int TIME = 0;
    private static final int WEEK_DAY = 1;
    private static final int DATE = 2;
    private static final int TIMET_COLS = 3;

    private static final int HERE = 1;
    private static final int NOT_HERE = -1;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        int[] weekdaysInSept = generateWeekdays();
        String[] students = getStudentTable(sc);
        int[][] inputTimetable = getTimeTableData(sc);
        int[][] timetable = createTimeTable(inputTimetable, weekdaysInSept);
        int[][] attendance = recordAtt(sc, students, timetable);
        putAll(timetable, students, attendance);
    }

    private static int countStud(String[] stud) {
        int i = 0;

        while ((i < MAX_STUDENTS) && !(stud[i].equals(""))) {
            i++;
        }
        return i;
    }

    private static int sizeOfTimeT(int[][] timet) {
        int i = 0;

        while ((i < MAX_LES_A_MONTH) && (timet[i][TIME] != 0)) {
            i++;
        }
        return i;
    }

    private static int getStudID(String[] stud, String name) {
        int i = 0;

        while ((i < MAX_STUDENTS) && !(stud[i].equals(name))) {
            i++;
        }
        return i;
    }

    private static int getLesID(int[][] timet, int[] lesson) {
        int i = 0;
        while (i < MAX_LES_A_MONTH) {
            if ((timet[i][TIME] == lesson[TIME])
            && timet[i][DATE] + 1 == lesson[DATE]) {
                break;
            }
            i++;
        }
        return i;
    }

    private static int[][] recordAtt(Scanner sc, String[]stud, int[][] timet) {
        int[][] att = new int[countStud(stud)][sizeOfTimeT(timet)];
        int[] lesson = new int[TIMET_COLS];

        while (sc.hasNext()) {
            String input = sc.nextLine();
            if (input.equals(".")) {
                break;
            }
            Scanner inputScan = new Scanner(input).useDelimiter(" ");
            int studID = getStudID(stud, inputScan.next());
            lesson[TIME] = inputScan.nextInt();
            lesson[DATE] = inputScan.nextInt();
            int lesID = getLesID(timet, lesson);
            int attendance = HERE;
            if (inputScan.nextLine().equals(" NOT_HERE")) {
                attendance = NOT_HERE;
            }
            att[studID][lesID] = attendance;
        }
        return att;
    }

    private static String getWeekCode(int i) {
        String[] weekdays = {"TU", "WE", "TH",
        "FR", "SA", "SU", "MO"};
        return weekdays[i];
    }

    private static void putTimetable(int curID, int[][]timet) {
        System.out.format("%10s", "");
        for (int i = 0; i < MAX_COLUMNS; i++, curID++) {
            if ((curID > MAX_LES_A_MONTH) || (timet[curID][TIME] == NO_DATA))
                break;
            System.out.format("%1d:00%3s%3d|", timet[curID][TIME], 
                                getWeekCode(timet[curID][WEEK_DAY]), 
                                timet[curID][DATE] + 1);
        }
        System.out.println("");
    }

    private static void putAttendance(int curID, int size, String[] studs, int[][] att) {
        int numstud = countStud(studs);

        for (int sID = 0; sID < numstud; sID++) {
            System.out.format("%10s", studs[sID]);
            int curIndex = curID;
            for (int i = 0; i < MAX_COLUMNS; i++) {
                if ((curIndex >= MAX_LES_A_MONTH) || (curIndex >= size)) {
                    break;
                }
                if (att[sID][curIndex] != 0) {
                    System.out.format("%10d|", att[sID][curIndex]);
                } else {
                    System.out.format("%10s|", "");
                }
                curIndex += 1;
            }
            System.out.println("");
        }
    }


    private static void putAll(int[][] timetable, String[] students, 
                            int[][] attendance) {
        int size = sizeOfTimeT(timetable);
        for (int curID = 0; curID < MAX_LES_A_MONTH; curID+= 10) {
            if (curID >= size) {
                break;
            }
            putTimetable(curID, timetable);
            putAttendance(curID, size, students, attendance);
            System.out.println("");
        }
        System.out.println("");
    }

    private static String[] getStudentTable(Scanner sc) {
        String[] students = new String[MAX_STUDENTS];
        int i = 0;

        while ((sc.hasNext()) && (i < MAX_STUDENTS)) {
            String input = sc.nextLine();
            if (input.equals(".")) {
                break;
            }
            students[i] = input;
            i++;
        }
        if (i == MAX_STUDENTS)
            System.out.println(".");
        while (i < MAX_STUDENTS) {
            students[i] = "";
            i++;
        }
        return students;
    }

    private static int[] generateWeekdays() {
        int[] month = new int[DAYS_SEPT];

        for (int i = 0; i < DAYS_SEPT; i++) {
            month[i] = i % DAYS_A_WEEK;
        }
        return month;
    }

    private static int[][] getTimeTableData(Scanner sc) {
        int[][] inputData = new int[MAX_LES_A_WEEK][3];
        int id = 0;

        while (id < MAX_LES_A_WEEK) {
            if (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    inputData[id][IN_TIME] = sc.nextInt();
                } else if (sc.nextLine().equals(".")) {
                    break;
                }
                inputData[id][IN_WEEKDAY] = makeWeekdayNumber(sc.nextLine());
                id++;
            }
        }
        if (id == MAX_LES_A_WEEK)
            System.out.println(".");
        return inputData;
    }

    private static int makeWeekdayNumber(String weekdayCode) {
        String[] weekdays = {" TU", " WE", " TH",
                            " FR", " SA", " SU", " MO"};
        int i = 0;
        while (i < DAYS_A_WEEK) {
            if (weekdayCode.equals(weekdays[i])) {
                break;
            }
            i++;
        }
        return i;
    }

    private static int getFirstWeekdaySept(int weekday, int[] weekdaysInSept) {
        int i = 0;
        while (weekdaysInSept[i] != weekday) {
            i++;
        }
        return i;
    }

    private static int countLesAWeek(int[][] inputTimetable) {
        int i = 0;
        while ((i < MAX_LES_A_WEEK) && (inputTimetable[i][IN_TIME] != NO_DATA)) {
            i += 1;
        }
        return i;
    }

    private static void swapNodes(int[][] inputData, int index) {
        int tmpTime = inputData[index][TIME];
        int tmpWeek = inputData[index][WEEK_DAY];

        inputData[index][TIME] = inputData[index + 1][TIME];
        inputData[index][WEEK_DAY] = inputData[index + 1][WEEK_DAY];
        inputData[index + 1][TIME] = tmpTime;
        inputData[index + 1][WEEK_DAY] = tmpWeek;
    }

    private static boolean nodeIsLater(int[] node1, int[] node2) {
        if (node1[WEEK_DAY] == node2[WEEK_DAY]) {
            return (node1[TIME] > node2[TIME]);
        }
        return (node1[WEEK_DAY] > node2[WEEK_DAY]);
    }

    private static void sortInputTimeTable(int[][] inputData) {
        int size = countLesAWeek(inputData);
        while (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                if (nodeIsLater(inputData[i], inputData[i + 1])) {
                    swapNodes(inputData, i);
                }
            }
            size--;
        }
    }

    private static void insertInTimeTable(int[][] timetable, int[][] input, 
                                        int[] weekdaysInSept) {
        int step = countLesAWeek(input);
        int date;
        int[] nodeToInsert;

        for (int i = 0; i < step; i++) {
            date = getFirstWeekdaySept(input[i][WEEK_DAY], weekdaysInSept);
            nodeToInsert = input[i];
            for (int j = i; j < MAX_LES_A_MONTH; j += step) {
                if (date > DAYS_SEPT) {
                    break;
                }
                timetable[j][TIME] = nodeToInsert[TIME];
                timetable[j][WEEK_DAY] = nodeToInsert[WEEK_DAY];
                timetable[j][DATE] = date;
                date += 7;
            }
        }
    }

    private static int[][] createTimeTable(int[][] inputTimetable, 
                                            int[] weekdaysInSept) {
        int[][] timetable = new int[MAX_LES_A_MONTH][4];

        sortInputTimeTable(inputTimetable);
        for (int i = 0; i < MAX_LES_A_WEEK; i++) {
            if (inputTimetable[i][IN_TIME] == NO_DATA) {
                break;
            }
            insertInTimeTable(timetable, inputTimetable, weekdaysInSept);
        }
        return timetable;
    }
}
