#Database

The database that is used in the Android project uses SQLite Database

The files that are used in the database are
1. [MySQLDatabaseHelper.java](https://github.com/HunterIT/APMRabbit/blob/master/Android/app/src/main/java/com/hunterit/APMRabbit/MySQLiteHelper.java "Go to Java File")  >> Helper Class
2. [DataSource.java](https://github.com/HunterIT/APMRabbit/blob/master/Android/app/src/main/java/com/hunterit/APMRabbit/DataSource.java "Go to Java File")DataSource.java			>> Class to create connections
3. [History.java](https://github.com/HunterIT/APMRabbit/blob/master/Android/app/src/main/java/com/hunterit/APMRabbit/History.java "Go to Java File")			>> UI of the waypoint history
4. [WayPoints.java](https://github.com/HunterIT/APMRabbit/blob/master/Android/app/src/main/java/com/hunterit/APMRabbit/WayPoints.java "Go to Java File")			>> Creates WayPoint object


##Structure

| NAME    |      TYPE     | INFO |
| ------------- |:-------------:| -----:|
| id    | auto-increment  | holds data info # |
| location     | VARCHAR(50)     |   hold the Log/Lit location of the Waypoint |
| timestamp | VARCHAR(50)     |    holds the current time the waypoint was made |
| name | VARCHAR(100)     |    holds the name of the waypoint |
