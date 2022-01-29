# **MyCleaningLog**
______________________________

### **Design Document**

Michael Ahlers

November Harris

Jeffrey Wallace

Yanling Zentgraf

Corin Manning

______________________________

### **Introduction**

Ever forget when the last time you cleaned something was? Our Cleaning Log will help you set reminders by room or area and keep track of when you clean. You add or remove rooms to the list and chores to each room. There are also options applicable to your whole house. Each item has it's own reminder that you can set to repeat however often you like. You can check off each task individually or by room as a whole. Additionally, if you check off vacuuming under the whole house option, it will check that off for each room. This will be a great app to add to your rotation to keep up on all your frequent and less frequent chores.

______________________________
### **Storyboard**

[MyCleaningLog Storyboard](https://projects.invisionapp.com/prototype/ckyiy7qe9004yss01a4emsp35/play)

______________________________
### **Functional Requirements**

### **Requirement 100.0: MyCleaningLog**

**SCENARIO**

As a user who needs to clean regularly, I want to have a way to remind me when I need to clean different items again so that I can keep my space clean and tidy.

**DEPENDENCIES**

Phones Internal Clock

List of Rooms and cleaning items contained

**ASSUMPTIONS**

Common cleaning tasks for each room

###### **EXAMPLES**

**1.1**

**Given** The list of rooms and items contained is available

**When** I search for “Bedroom"

**Then** A bedroom should be added to my list of rooms that is automatically populated with common tasks such as:

Vaccum

Dust

Wash Bedding

Clean Baseboards

**1.2**

**Given** The list of rooms and items contained is available

**When** I search for “Mike's Toy Shop"

**Then** I should recieve a notification of "No prebuilt rooms found. Would you like to create one? Y/N"



**2.1**

**Given** The application is synced to the phone's internal clock

**When** I select a task to view that has not gone past due

**Then** I should see a timer counting down to zero indicating the time until the task needs to be completed again


**2.2**

**Given** The application is synced to the phone's internal clock

**When** I select a task to view that has gone past due

**Then** I should see a red timer counting up from the time since it was supposed to be completed



**3.1**

**Given** A task has never been used

**When** I select it

**Then** I should be prompted to set the time between completing the task and the next time it needs to be done (cooldown)


**3.2**

**Given** A task has never been used

**When** I select it and choose not to set a cooldown time

**Then** The cooldown time should be set to a default value

______________________________

### **CLASS DIAGRAM**

![diagram](/UML/CleanList.drawio.png)

______________________________

### **Design Document**


______________________________

### **SIGNOFF**



**DevOps/Product Owner/Scrum Master** Michael Ahlers

**Frontend Developer** Jeffrey Wallace, Corin Manning

**Integration Developer** November Harris

**Quality Control / Testing** Yanling Zentgraf
		
		


