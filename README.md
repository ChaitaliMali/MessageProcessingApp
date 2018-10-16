# Message Processing Application


Message processing (standalone) application for processing sales notification messages.

# Assumptions:

•	Messages will come in text format.

•	The messages will have the name of a product in singluar format
   		 e.g. “apple at 10p” and “2 sales of apple at 10p each”
          
•	Currency in INR (Indian rupee)

•	The sender will not recieve any response

•	After recieving any TYPE 3 MESSAGE eg. Add 20p apple, if another message recieved is of TYPE 2: 2 sales of apple at 10p each, then the    value will be over written 

•	The data is stored in inmemory H2 database

•	quantity field in Product DO refers to the number of sales

•	Adjustments done to any products are not counted as a sales /quantity count


# How to run the application?

  We can run the application from Main class.

# Improvement:

•	The application should be intelligent enough to identify singular and plural names such as apple and apples.

•	Data stored in memory can be done via caching technique.

•	Log4j can be used for logging the logs in a file.

•	Use custom exceptions and handle it in more efficient way

•	Logging of the adjustments made can be handled in a different way



 


