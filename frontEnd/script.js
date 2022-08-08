let VanillaCalendar = (function () {
  function VanillaCalendar(options) {
      function addEvent(el, type, handler){
          if (!el) return
          if (el.attachEvent) el.attachEvent('on' + type, handler)
          else el.addEventListener(type, handler);
      }
      function removeEvent(el, type, handler){
          if (!el) return
          if (el.detachEvent) el.detachEvent('on' + type, handler)
          else el.removeEventListener(type, handler);
      }
      let opts = {
          selector: null,
          datesFilter: false,
          pastDates: true,
          availableWeekDays: [],
          availableDates: [],
          date: new Date(),
          todaysDate: new Date(),
          button_prev: null,
          button_next: null,
          month: null,
          month_label: null,
          onSelect: (data, elem) => {},
          months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
          shortWeekday: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
      }
      for (let k in options) if (opts.hasOwnProperty(k)) opts[k] = options[k]
      
      let element = document.querySelector(opts.selector)
      if (!element)
          return
      
      const getWeekDay = function (day) {
          return ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'][day]
      }
      
      const createDay = function (date) {
          let newDayElem = document.createElement('div')
          let dateElem = document.createElement('span')
          dateElem.innerHTML = date.getDate()
          newDayElem.className = 'vanilla-calendar-date'
          newDayElem.setAttribute('data-calendar-date', date)
          
          let available_week_day = opts.availableWeekDays.filter(f => f.day === date.getDay() || f.day === getWeekDay(date.getDay()))
          let available_date = opts.availableDates.filter(f => f.date === (date.getFullYear() + '-' + String(date.getMonth() + 1).padStart('2', 0) + '-' + String(date.getDate()).padStart('2', 0)))
          
          if (date.getDate() === 1) {
              newDayElem.style.marginLeft = ((date.getDay()) * 14.28) + '%'
          }
          if (opts.date.getTime() <= opts.todaysDate.getTime() - 1 && !opts.pastDates) {
              newDayElem.classList.add('vanilla-calendar-date--disabled')
          } else {
              if (opts.datesFilter) {
                  if (available_week_day.length) {
                      newDayElem.classList.add('vanilla-calendar-date--active')
                      newDayElem.setAttribute('data-calendar-data', JSON.stringify(available_week_day[0]))
                      newDayElem.setAttribute('data-calendar-status', 'active')
                  } else if (available_date.length) {
                      newDayElem.classList.add('vanilla-calendar-date--active')
                      newDayElem.setAttribute('data-calendar-data', JSON.stringify(available_date[0]))
                      newDayElem.setAttribute('data-calendar-status', 'active')
                  } else {
                      newDayElem.classList.add('vanilla-calendar-date--disabled')
                  }
              } else {
                  newDayElem.classList.add('vanilla-calendar-date--active')
                  newDayElem.setAttribute('data-calendar-status', 'active')
              }
          }
          if (date.toString() === opts.todaysDate.toString()) {
              newDayElem.classList.add('vanilla-calendar-date--today')
          }
          
          newDayElem.appendChild(dateElem)
          opts.month.appendChild(newDayElem)
      }
      
      const removeActiveClass = function () {
          document.querySelectorAll('.vanilla-calendar-date--selected').forEach(s => {
              s.classList.remove('vanilla-calendar-date--selected')
          })
      }
      
      const selectDate = function () {
          let activeDates = element.querySelectorAll('[data-calendar-status=active]')
          activeDates.forEach(date => {
              date.addEventListener('click', function () {
                  removeActiveClass()
                  let datas = this.dataset
                  let data = {}
                  if (datas.calendarDate)
                      data.date = datas.calendarDate
                  if (datas.calendarData)
                      data.data = JSON.parse(datas.calendarData)

                      let tmp1 = datas.calendarDate;
                      let month = tmp1.slice(4,7);
                      let datee = tmp1.slice(8,10);
                      let yearr = tmp1.slice(11,15);
                      if (month == "Jan") { month = 1; } else if(month == "Feb"){ month = 2; } else if(month == "Mar"){ month = 3; } else if(month == "Apr"){ month = 4; } else if(month == "May"){ month = 5; } else if(month == "Jun"){ month = 6; } else if(month == "Jul"){ month = 7; } else if(month == "Aug"){ month = 8; } else if(month == "Sep"){ month = 9; } else if(month == "Oct"){ month = 10; } else if(month == "Nov"){ month = 11; } else if(month == "Dec"){ month = 12; }
                  opts.onSelect(data, this,month,datee,yearr)
                  this.classList.add('vanilla-calendar-date--selected')
              })
          })
      }
      
      const createMonth = function () {
          clearCalendar()
          let currentMonth = opts.date.getMonth()
          while (opts.date.getMonth() === currentMonth) {
              createDay(opts.date)
              opts.date.setDate(opts.date.getDate() + 1)
          }
          
          opts.date.setDate(1)
          opts.date.setMonth(opts.date.getMonth() -1)
          opts.month_label.innerHTML = opts.months[opts.date.getMonth()] + ' ' + opts.date.getFullYear()
          selectDate()
      }
      
      const monthPrev = function () {
          opts.date.setMonth(opts.date.getMonth() - 1)
          createMonth()
      }
      
      const monthNext = function () {
          opts.date.setMonth(opts.date.getMonth() + 1)
          createMonth()
      }
      
      const clearCalendar = function () {
          opts.month.innerHTML = ''
      }
      
     


      const createCalendar = function () {
          document.querySelector(opts.selector).innerHTML = `
          <div class="vanilla-calendar-header">
              <button type="button" class="vanilla-calendar-btn" data-calendar-toggle="previous"><svg height="24" version="1.1" viewbox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M20,11V13H8L13.5,18.5L12.08,19.92L4.16,12L12.08,4.08L13.5,5.5L8,11H20Z"></path></svg></button>
              <div class="vanilla-calendar-header__label" data-calendar-label="month"></div>
              <button type="button" class="vanilla-calendar-btn" data-calendar-toggle="next"><svg height="24" version="1.1" viewbox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M4,11V13H16L10.5,18.5L11.92,19.92L19.84,12L11.92,4.08L10.5,5.5L16,11H4Z"></path></svg></button>
          </div>
          <div class="vanilla-calendar-week"></div>
          <div class="vanilla-calendar-body" data-calendar-area="month"></div>
          `
      }
      const setWeekDayHeader = function () {
          document.querySelector(`${opts.selector} .vanilla-calendar-week`).innerHTML = `
              <span>${opts.shortWeekday[0]}</span>
              <span>${opts.shortWeekday[1]}</span>
              <span>${opts.shortWeekday[2]}</span>
              <span>${opts.shortWeekday[3]}</span>
              <span>${opts.shortWeekday[4]}</span>
              <span>${opts.shortWeekday[5]}</span>
              <span>${opts.shortWeekday[6]}</span>
          `
      }
      
      this.init = function () {
          createCalendar()
          opts.button_prev = document.querySelector(opts.selector + ' [data-calendar-toggle=previous]')
          opts.button_next = document.querySelector(opts.selector + ' [data-calendar-toggle=next]')
          opts.month = document.querySelector(opts.selector + ' [data-calendar-area=month]')
          opts.month_label = document.querySelector(opts.selector + ' [data-calendar-label=month]')
          
          opts.date.setDate(1)
          createMonth()
          setWeekDayHeader()
          addEvent(opts.button_prev, 'click', monthPrev)
          addEvent(opts.button_next, 'click', monthNext)
      }
      
      this.destroy = function () {
          removeEvent(opts.button_prev, 'click', monthPrev)
          removeEvent(opts.button_next, 'click', monthNext)
          clearCalendar()
          document.querySelector(opts.selector).innerHTML = ''
      }
      
      this.reset = function () {
          this.destroy()
          this.init()
      }
      
      this.set = function (options) {
          for (let k in options)
              if (opts.hasOwnProperty(k))
                  opts[k] = options[k]
          createMonth()
//             this.reset()
      }
      
      this.init()
  }
  return VanillaCalendar
})()

window.VanillaCalendar = VanillaCalendar

let userId     = null;
let userName   = null;
let selectedTrackerId = null; 
let trackers           = [];
let trackersLastData   = [];

let date;
let lat = 0;
let lng = 0;



//const url='http://test.neosystems.cc:8081/last';
//const url1='http://test.neosystems.cc:8081/date/';
const url   ='http://localhost:8082/tracker/lasts/';
const url1  ='http://localhost:8082/date/';
const url2  ='http://localhost:8082/user/login';
const   url3  ='http://localhost:8082/tracker';




var greenGetz = L.icon({
  iconUrl: 'getz.png',
  iconSize:     [110, 110], // size of the icon
  iconAnchor:   [110, 110], // point of the icon which will correspond to marker's location
  popupAnchor:  [-60, -70] // point from which the popup should open relative to the iconAnchor
});

// config map
let config = {
  minZoom: 7,
  maxZoom: 18,
};
// magnification with which the map will start
const zoom = 18;
// co-ordinates


// calling map
const map = L.map("map", config).setView([lat, lng], zoom);

// Used to load and display tile layers on the map
// Most tile servers require attribution, which you can set under `Layer`
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution:
    '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
}).addTo(map);



/*

let marker = L.marker([lat, lng],{icon: greenGetz});
marker.addTo(map).bindPopup("Getz");
*/



//////////////////// Create  markers based on trackers
var markers = {};

function setMarkers() {
  console.log("hello")
  for (let i = 0; i < trackers.length; i++) {
   
   if(!(trackers[i][0] in markers)){

    for (let j = 0; j < trackersLastData.length; j++) {
      if(trackersLastData[j][0]==trackers[i][0]){
       markers[trackers[i][0]] = new L.marker([trackersLastData[j][1], trackersLastData[j][2]]).addTo(map).bindPopup(trackers[i][2]);}
   }
  }
   else{

    for (let j = 0; j < trackersLastData.length; j++) {
      if(trackersLastData[j][0]==trackers[i][0]){
       markers[trackers[i][0]].setLatLng([trackersLastData[j][1], trackersLastData[j][2]]).update();
   }}
    
}
  }
  }





//////////////////////get last trackerData


const Http = new XMLHttpRequest();
Http.onreadystatechange = (e) => {
  if (Http.readyState === 4 && Http.status === 200) {
    trackersLastData   = [];
    var json = JSON.parse(Http.responseText);
    for (let i = 0; i < json.length; i++) {
     trackersLastData.push([json[i].trackerId, json[i].latitude, json[i].longitude, json[i].speed, json[i].heading])
    }
    
    console.log(trackersLastData);

  }

}


setInterval(function(){
  Http.open("GET", url + String(userId));
  Http.send();


  for (let i = 0; i < trackersLastData.length; i++) {

    if(selectedTrackerId == trackersLastData[i][0]){
      console.log(trackersLastData[i][0])
      document.getElementById("text_speed").innerHTML          = trackersLastData[i][3];
      document.getElementById("text_head").innerHTML           = trackersLastData[i][4];
      document.getElementById("text_fuel").innerHTML           = trackersLastData[i][4];//just some data
      document.getElementById("text_engTemp").innerHTML        = trackersLastData[i][4];
      document.getElementById("text_faults").innerHTML         = trackersLastData[i][4];
      document.getElementById("text_speedGps").innerHTML       = trackersLastData[i][4];
      document.getElementById("text_carBattery").innerHTML     = trackersLastData[i][4];
      document.getElementById("text_trackerBattery").innerHTML = trackersLastData[i][4];

    }

  }

  setMarkers();
    

    
}, 10000);


function btnclick() {
  for (let i = 0; i< trackersLastData.length; i++) {
    if(selectedTrackerId == trackersLastData[i][0]){
       map.setView([trackersLastData[i][1], trackersLastData[i][2]]);
 }
}
}

// --------------------------------------------------
// sidebar

const menuItems = document.querySelectorAll(".menu-item");
const sidebar = document.querySelector(".sidebar");
const buttonClose = document.querySelector(".close-button");

menuItems.forEach((item) => {
  item.addEventListener("click", (e) => {
    const target = e.target;

    if (
      target.classList.contains("active-item") ||
      !document.querySelector(".active-sidebar")
    ) {
      document.body.classList.toggle("active-sidebar");
    }

    // show content
    showContent(target.dataset.item);
    // add active class to menu item
    addRemoveActiveItem(target, "active-item");
  });
});

// close sidebar when click on close button
buttonClose.addEventListener("click", () => {
  closeSidebar();
});

// remove active class from menu item and content
function addRemoveActiveItem(target, className) {
  const element = document.querySelector(`.${className}`);
  target.classList.add(className);
  if (!element) return;
  element.classList.remove(className);
}

// show specific content
function showContent(dataContent) {
  const idItem = document.querySelector(`#${dataContent}`);
  addRemoveActiveItem(idItem, "active-content");
}

// --------------------------------------------------
// close when click esc
document.addEventListener("keydown", function (event) {
  if (event.key === "Escape") {
    closeSidebar();
  }
});



// --------------------------------------------------
// close sidebar

function closeSidebar() {
  document.body.classList.remove("active-sidebar");
  const element = document.querySelector(".active-item");
  const activeContent = document.querySelector(".active-content");
  if (!element) return;
  element.classList.remove("active-item");
  activeContent.classList.remove("active-content");
  clearMap();
}




//-------------------------------------------------



//////////////////////////////////////////////////calendar

let g_date = -1
let g_month = -1
let g_year = -1
            

let calendar = new VanillaCalendar({
    selector: "#myCalendar",
    months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    shortWeekday: ['Sun','Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    onSelect: (data, elem, month, date, year) => {
      g_date=date
      g_month=month
      g_year=year
    
    }
})

function clearMap() {
  for(i in map._layers) {
      if(map._layers[i]._path != undefined) {
          try {
              map.removeLayer(map._layers[i]);
          }
          catch(e) {
              console.log("problem with " + e + map._layers[i]);
          }
      }
  }
}

/////////////////////search by Date

const Http1 = new XMLHttpRequest();
Http1.onreadystatechange = (e) => {
  var json = JSON.parse(Http1.responseText);
  console.log(json.length)
  let points = [];
  for (let i = 0; i < json.length; i++) {
    points.push([json[i].latitude , json[i].longitude])
  }
  console.log(points)
  // add polyline to map
L.polyline(points, {
  color: "red",
  opacity: 0.5,
  weight: 20,
})
  .bindPopup("polygon")
  .addTo(map);

  map.setView([json[0].latitude , json[0].longitude]);
  
}


const btn = document.querySelector('#btn');
const h_s = document.querySelector('#hours_s')
const h_f = document.querySelector('#hours_f')
btn.onclick = (event) => {
  event.preventDefault();
  clearMap();
if(g_date != -1 || g_month != -1 || g_year != -1){
    if( h_f.selectedIndex >= h_s.selectedIndex ) {
      let tmp = parseInt(h_f.value);
      tmp = tmp + 1;
      if( tmp < 10 ) {
      h_s.value = "0" + String(tmp);
      }
      else{
        h_s.value = String(tmp);
      }
}

    Http1.open("GET", url1 + String(selectedTrackerId) + "/" + g_date + "/" + g_month + "/" + g_year + "/" + String(h_f.value) + "/" + String(h_s.value) );
    Http1.send();

}
else{
  alert("Please select date first");
}

};






/////////////////////add to tracker list

const frontTrackersList = document.querySelector('#fronTrackers');

function addLists() {

  let tempArrayTrackers = [];

  Array.from(document.querySelector("#fronTrackers").options).forEach(function(option_element) {
    let option_text = option_element.text;
    let option_value = option_element.value;
    let is_option_selected = option_element.selected;
    tempArrayTrackers.push(option_value)
  });
console.log(tempArrayTrackers)

    for (var i = 0; i < trackers.length; i++) {
      if(! tempArrayTrackers.includes(trackers[i][2])){
        var el = document.createElement("option");
        el.textContent = trackers[i][2];
        el.value       = trackers[i][2];
        frontTrackersList.appendChild(el);
      }
      else{
        console.log(trackers[i][2] + "  Already in list");
      }
    }  

    selectedTrackerId = trackers[0][0];                                    
}



const selectElement = document.querySelector('#fronTrackers');

selectElement.addEventListener('change', (event) => {

  for (var i = 0; i < trackers.length; i++) {
  if(String(trackers[i][2]) ==  String(event.target.value)){
    selectedTrackerId = trackers[i][0];
    console.log(selectedTrackerId)
  }
   } 

});




/////////////////////LogIn User

var Http2 = new XMLHttpRequest();
Http2.onreadystatechange = function () {
    if (Http2.readyState === 4 && Http2.status === 200) {
      trackers   = [];
      var json = JSON.parse(Http2.responseText);
      for (let i = 0; i < json.length; i++) {
        trackers.push([json[i].id , json[i].hardwareId , json[i].name])
      }
      
      console.log(trackers);
      addLists();
    
    }
 
}



var UserName = document.getElementById("LName");
var Password = document.getElementById("LPass");

function btnLogInclick() {

  if (UserName.value.length < 4) {
    alert("Inavalid username");
  } 
  if (Password.value.length < 4) {
    alert("Inavalid password");
  } 



var xhr = new XMLHttpRequest();
xhr.open("POST", url2, true);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        console.log(json.id + ", " + json.username+ ", " +json.password);
        userId   = json.id;
        userName = json.username;

        Http2.open("GET", url3 + "/" + String(userId));
        Http2.send();

        alert("Logged In Succesfully as "+ userName + " " + userId);

    }
    
};
var data = JSON.stringify({"password": Password.value, "username": UserName.value});
xhr.send(data);

}


/////////////////////add tracker

var hardwareId   = document.getElementById("hardwareId");
var hardwareName = document.getElementById("hardwareName");


function btnAddTrackerclick() {
  if (hardwareId.value.length < 4) {
    alert("Inavalid hardwareId" );
  } 
  if (hardwareName.value.length < 4) {
    alert("Inavalid hardware name");
  } 

   var xhr = new XMLHttpRequest();
   xhr.open("POST", url3, true);
   xhr.setRequestHeader("Content-Type", "application/json");
   xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        console.log(json.id + ", " + json.hardwareId+ ", " +json.name);
        alert(json.name + "Added succesfully ");

        Http2.open("GET", url3 + "/" + String(userId));
        Http2.send();

    }
   
};
var data = JSON.stringify({"name": hardwareName.value, "hardwareId": hardwareId.value, "userId": userId});
xhr.send(data);
}






