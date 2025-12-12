# db-anonymiser
Database data anonymiser based on GDPR rules

Prerequisites:
- Java 17
- The jar file of the dbanonymiser
- a config file in JSON format to anonymise the database
- Supported databases: MS SQL Server, PostgreSQL, Oracle

To run the program, you have to launch it with a JSON file as argument. 

- java -jar dbanonymizer.jar application.json

Increase the Xmx size if you run into OutofMemoryError. The application typically uses between 200 and 400 Mb to run.

This JSON file will contain basically two sections:

1) Datasources

Example:
	"databases": [
		{
			"refId": "myuniqueRefId1",
			"driverClassName": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"url": "jdbc:sqlserver://;serverName=myservername1;databaseName=mydatabasename1;encrypt=true;trustServerCertificate=true",
			"username": "test",
			"password": "test"
		},
		{
			"refId": "myuniqueRefId2",
			"driverClassName": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"url": "jdbc:sqlserver://;serverName=myservername2;databaseName=mydatabasename2;encrypt=true;trustServerCertificate=true",
			"username": "test",
			"password": "test"
		}
	],
	...
	
The refId element is important and must be unique for each datasource.

2) Anonymisation rules

Example:
   "anonymisations": [{
            "tableName": "activity_sheet_entity",
            "columnName": "created_by",
            "anonymiser": "SIMPLE_NAME",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "modified_by",
            "anonymiser": "SIMPLE_NAME",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "buyer_company_name",
            "anonymiser": "COMPANY_NAME",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "supplier_company_name",
            "anonymiser": "COMPANY_NAME",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "employee_name",
            "anonymiser": "FULL_NAME",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "supplier_email",
            "anonymiser": "EMAIL",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "employee_email",
            "anonymiser": "EMAIL",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "activity_sheet_entity",
            "columnName": "project_manager_email",
            "anonymiser": "EMAIL",
            "databaseRefId": "isupplier_db_activity"
        },
        {
            "tableName": "contract_entity",
            "columnName": "created_by",
            "anonymiser": "SIMPLE_NAME",
            "databaseRefId": "isupplier_db_contract"
        },
        {
            "tableName": "contract_entity",
            "columnName": "modified_by",
            "anonymiser": "SIMPLE_NAME",
            "databaseRefId": "isupplier_db_contract"
        },
        {
            "tableName": "contract_entity",
            "columnName": "buyer_name",
            "anonymiser": "COMPANY_NAME",
            "databaseRefId": "isupplier_db_contract"
        },
        {
            "tableName": "contract_entity",
            "columnName": "sent_to",
            "anonymiser": "EMAIL",
            "databaseRefId": "isupplier_db_contract"
        }
        ...
    ],
		
		
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
			"databaseRefId": "myuniqueRefId2"
		},
    
That's it.

