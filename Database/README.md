#Database

The database that is used in the Android project uses SQLite Database

The files that are used in the database are
	- MySQLDatabaseHelper.java  >> Helper Class
	- DataSource.java			>> Class to create connections
	- History.java				>> UI of the waypoint history
	- WayPoints.java			>> Creates WayPoint object

##Structure

| NAME    |      TYPE     | INFO |
| ------------- |:-------------:| -----:|
| id    | auto-increment  | holds data info # |
| location     | VARCHAR(50)     |   hold the Log/Lit location of the Waypoint |
| timestamp | VARCHAR(50)     |    holds the current time the waypoint was made |
| name | VARCHAR(100)     |    holds the name of the waypoint |
