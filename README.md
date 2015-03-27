# Final-Project-Code
All of the code used to create the Letting Agency system.

Setting up the Database
---------------------
1. Load the lettings_agency.sql file, located in the 'SQL Code' folder, in Oracle SQL Developer using a valid connection.
2. Run the script to create the required tables and populate the tables with encoded data.
3. View the Person, Secure Person, Maintanence, Landlord and Houses tables on the SQL Developer to ensure the data has been added.

Setting up the Applications
---------------------------
1. Firstly, open the following server Java classes in a Java editor, e.g. Eclipse IDE - LandlordServer, ClientLoginServer, StaffServer, MaintanenceServer, 
   CalandarServer, HousesServer and MessagingServer. These can be found in the GeneralServer, StaffServer, LandlordServer and ClientServer folders.
2. Locate the code that connects the the database (found within the connectDatabase() method). Alter the Username, Password, Host, SID and Port details to match 
   your database connection properties.
3. Create a Tomcat Version 7.0 server.
4. Next, open the following GUI Java class in a Java editor - MainApplication in ClientApplication and in StaffApplication, AddTask in ClientApplication and in 
   StaffApplication, NewMessage in ClientApplication and in StaffApplication, NewClient in ClientApplication and in StaffApplication, LogIn in ClientApplication
   and in StaffApplication, NewHouse in StaffApplication, NewMaintanence in StaffApplication, NewLandlord in StaffApplication and NewStaff in StaffApplication
5. All of these files contain a peace of code that connects to the server applications (all of which are defined as Global Variables). Currently, they search for 
   the classes at Localhost on Port 8888. This will need to be changed on each class to reflect your current server setup.
6. Run the server projects, GeneralServer, ClientServer, StaffServer and LandlordServer, on the Tomcat Version 7.0 server setup in step 3.
7. Run the LogIn class for the Application you desire. These are located in ClientApplication and StaffApplication.
8. If problems occur, ensure all server and database information is correct.

If problems persist, feel free to contact me at christopher.graves@stu.mmu.ac.uk and I will help set up the application.
