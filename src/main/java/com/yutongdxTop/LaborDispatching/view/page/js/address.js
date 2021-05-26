var address = //var address = "http://yutongdx.top:8080/";
    "http://localhost:8080/";

var projectUrl = address + 'LaborDispatching/project/getAllProjects' + '?timestamp=' + new Date().getTime();
    //projectUrl = '../testjson/project.json';

var clientUrl = address + 'LaborDispatching/clientVo/getAllClientVos' + '?timestamp=' + new Date().getTime();
    //clientUrl = '../testjson/client.json';

var freeTimeUrl = address + 'LaborDispatching/freelancer/getAllFreeTimes' + '?timestamp=' + new Date().getTime();
    //freeTimeUrl = '../testjson/freeTime.json';

var contactUrl = address + 'LaborDispatching/freelancer/getAllContacts' + '?timestamp=' + new Date().getTime();
    //contactUrl = '../testjson/contact.json';

var freelancerUrl = address + 'LaborDispatching/staffVo/getAllFreelancers' + '?timestamp=' + new Date().getTime();

var staffUrl = address + 'LaborDispatching/staff/getAllStaffs' + '?timestamp=' + new Date().getTime();

var freeTimeVoUrl = address + 'LaborDispatching/administrator/getAllFreeTimeVos' + '?timestamp=' + new Date().getTime();

var contactVoUrl = address + 'LaborDispatching/administrator/getAllContactVos' + '?timestamp=' + new Date().getTime();