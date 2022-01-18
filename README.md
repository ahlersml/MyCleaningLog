# **MyCleaningLog**
______________________________

### **Design Document**

Michael Ahlers
November Harris
Jeffrey Wallace

______________________________

### **Introduction**


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

List of Rooms and cleaning items contained.

**ASSUMPTIONS**

Common cleaning items for each room

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

**When** I search for “Mikes Toy Shop"

**Then** I should recieve a notification of "Zero prebuilt rooms found. Would you Like to create anyway? Y/N"




**2.1**

**Given** The application is synched to the phones internal clock

**When** I select a task to view that has not gone past due

**Then** I should see a timer counting down to zero indicating the time until the task needs to be completed again


**2.2**

**Given** The application is synched to the phones internal clock

**When** I select a task to view that has gone past due

**Then** I should see a red timer counting up from the time since it was supposed to be completed


______________________________

### **CLASS DIAGRAM**


______________________________

### **SIGNOFF**



**DevOps/Product Owner/Scrum Master** Michael Ahlers

**Frontend Developer** Jeffrey Wallace

**Integration Developer** November Harris
		
		


