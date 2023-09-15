**Prerequisites:**
•	IntelliJ IDE
•	Scala 2.13.12
•	Java Development Kit (JDK) 1.8 or Later 
•	Download Scala and SBT plugins.
•	Operating System- Any

**Steps of Execution:**
•	Clone the GitHub Repository using git link-  'https://github.com/minalmahajan3/task_price_basket.git' or can follow the 2nd step also.
•	Open IntelliJ IDE 
•	Click on "Get from Version Control" or go to File -> New -> Project from Version Control -> Git.
•	In the "URL" field, enter the URL of the GitHub repository you want to clone. For example, if your repository is at,'https://github.com/minalmahajan3/task_price_basket.git', enter that URL.
•	Choose a directory where you want to save the project on your local machine in the "Directory" field if not selected it saves the project in the default path(idealProjects)
•	Click the "Clone" button to clone the repository.
•	Once cloning project is finished.
•	Click on the task_price_basket[shopping_basket] -> drop down option click on src -> main -> scala -> 'PriceBasket'. Double click on PriceBasket will open the main scala code.
•	For unit tests file -> click on the task_price_basket[shopping_basket] -> drop down option click on src -> main -> test -> scala -> 'PriceBasketSpec' is a Unit test case file.
•	To run scala code file i.e., PriceBasket -> first click on ‘PriceBasket’ or current file on top-middle and then go to the Edit Configuration option 
-> click on application 
-> add JDK path (Java 1.8), 
-> select Module which is nothing but Project name i.e., ‘shopping_basket’ and provide main class as Price_Basket,
-> provide input in 'program arguments' section as for example Apples Milk Bread. Make sure to keep space in between arguments. (These can be of any combination like Soup Soup Bread Apples, Apples Bread Apples).  
•	After finishing the above steps click on Apply and ok. Now we are ready to click the run option. In this way we can execute the Scala program and output will be displayed in the IntelliJ command-line
•	For executing unit test file, we need to click on Run option, and we can observe the output on IntelliJ command-line.

