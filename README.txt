# db-anonymiser
Database data anonymiser based on RGPD rules

Prerequisites:
- Java 17
- The jar file of the dbanonymiser
- a config file in JSON format to anonymise the database
- Supported databases: MS SQL Server, PostgreSQL, Oracle (untested)

To run the program, you have to launch it with a JSON file as argument. 

- java -jar dbanonymizer.jar application.json

Increase the Xmx size if you run into OutofMemoryError. The application typically uses between 200 and 400 Mb to run.

This JSON file will contain basically two sections:

1) Datasources

Example:
	"databases": [
		{
			"refId": "maccs_Bunkering_V2_staging",
			"driverClassName": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"url": "jdbc:sqlserver://;serverName=sql01.stgmaccs.oci.ldn.com;databaseName=Bunkering_V2;encrypt=true;trustServerCertificate=true",
			"username": "test",
			"password": "test"
		},
		{
			"refId": "maccs_ASPNETDB_staging",
			"driverClassName": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"url": "jdbc:sqlserver://;serverName=sql01.stgmaccs.oci.ldn.com;databaseName=ASPNETDB;encrypt=true;trustServerCertificate=true",
			"username": "test",
			"password": "test"
		}
	],
	...
	
The refId element is important and must be unique for each datasource.

2) Anonymisation rules

Example:
		{
			"tableName": "Bunkering_Request",
			"primaryKey": "Request_ID",
			"columnName": "Ship_Name",
			"anonymiser": "COMPANY_NAME",
			"databaseRefId": "maccs_Bunkering_V2_staging"
		},
		{
			"tableName": "Bunkering_Request",
			"primaryKey": "Request_ID",
			"columnName": "IMO_No",
			"anonymiser": "ALPHANUM",
			"databaseRefId": "maccs_Bunkering_V2_staging"
		},
		...
		
Explanation:
- table name: the table name you want to anonymize
- primaryKey: the primary key of the table. This is used to update the values after anonymisation.
- column name: the name of the column to anonymize in the table.
- anonymizser: The type of anonymiser to use. They can take one of the following values:
    - COMPANY_NAME
    - CREDIT_CARD
    - DATE
    - EMAIL
    - FIRST_NAME
    - FULL_NAME
    - LAST_NAME
    - PHONE_NUMBER
    - SIMPLE_NAME
    - CITY
    - POST_CODE
    - STREET_NAME
    - ALPHANUM
    - LITTERAL
    If you are using the 'LITTERAL' anonymiser, it means you have to specify the exact value to use as anonymisation, by adding the 'litteralValue' attribute.
    Example: 
    		{
			"tableName": "Company",
			"primaryKey": "Company_Id",
			"columnName": "BRN",
			"anonymiser": "LITTERAL",
			"litteralValue": "00000",
			"databaseRefId": "maccs_ASPNETDB_staging"
		},
    
That's it.

