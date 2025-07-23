CRM-Project (Education CRM Project)
Note: The Employee Module has not been added yet.
=> CRM (Customer Relationship Management) :- -> Uses of CRM: 1. Efficient Customer Data Management 2. Providing Customer Services and Support 3. Managing Leads and Follow-Ups 4. Product Management Including Product & Sales Tracking etc


=> Education CRM Project :-

-> Introduction :-
	= This project streamlines customer relationship management by handling student data, course sales, support services and lead tracking; enhancing overall efficiency and customer satisfaction in educational institutes.
	
-> Modules :-
	1. Admin
	2. Employee
	3. Customer (Student or User)
	
-> Requirements / Documentation :-
	1. Admin :-
		= Login
		= Profile Management (view, update)
		= Employee Management (add, view, update)
		= Courses Management (add, view, update, delete)
		= Sales Monitoring (view sales, analytics)
		= Customer Management (view, search, edit)
		= Feedback (collect, alalyze)
		  etc
	2. Employee :-
		= Login
		= Profile Management (view, update)
		= Handle Customer Inquiries
		= Lead Management (discussion, follow-ups)
		  etc
	3. Customer (Student or User) :-
		= Login & Register
		= View Available Courses
		= Purchase Courses
		= Provide Feedback
		  etc
		  
-> Database Tables :-
	1. register
		= id, name, email, password, phoneno, module
	2. cusomer_register
		= id, name, email, password, phoneno, city
	3. sales
		= id, customer_email, course, price, date, time
	4. cusomer_inqueries
		= id, phoneno, name, interested_course, discussion, inquiry_date, inquiry_time, follow_up_date, calling_details (who called to whome)
	etc
	
-> Technologies Used :-
	= Front-end (HTML, CSS, JavaScript, jQuery, Bootstrap, AJAX)
	= Back-end (Spring Boot WEB-MVC)
	= Database (MySQL)
	= Database Technology (Spring Boot DataJPA)
