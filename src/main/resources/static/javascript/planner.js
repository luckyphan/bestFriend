const cookieArr = document.cookie.split("=")
const petId = cookieArr[1]

//access DOM elements
const submitForm = document.getElementById("schedule-form")
const scheduleContainer = document.getElementById("schedule-container")
//get event's name and date/time
const eventName = document.getElementById("schedule-name")
const eventDate = document.getElementById("dayTime")

//Modal Elements
let eventNameBody = document.getElementById("schedule-name-body")
let eventDateBody = document.getElementById("dayTimeBody")
let updateEventBtn = document.getElementById('update-schedule-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/schedules/"


const handleSubmit = async (e) => {
    e.preventDefault()
    if(
        eventName.value === '' ||
        eventDate.value === ''
    ){
         alert('Please fill out the name, day, and time of your pet\'s schedule :)')
         return
     }
     console.log("this is the event date "+eventDate)
    let bodyObj = {
        event: eventName.value,
        eventDate: eventDate.value,
        eventTime: eventDate.value
    }
    console.log(bodyObj)
    await addSchedule(bodyObj,petId);
}

async function addSchedule(obj,petId) {
    const response = await fetch(`${baseUrl}pet/${petId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getAllSchedules(petId);
    }
}

async function getAllSchedules(petId) {
    await fetch(`${baseUrl}pet/${petId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => {
        console.log(data)
        createScheduleCards(data)})
        .catch(err => console.error(err))
}

async function handleDelete(scheduleId){
    await fetch(baseUrl + scheduleId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getAllSchedules(petId);
}

async function getSchedule(scheduleId){
    await fetch(baseUrl + scheduleId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleScheduleEdit(scheduleId){
    let bodyObj = {
        id: scheduleId,
        event: eventNameBody.value,
        eventDate: eventDateBody.value,
        eventTime: eventDateBody.value
    }
    console.log(bodyObj)

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getAllSchedules(petId);
}

const createScheduleCards = (array) => {
    scheduleContainer.innerHTML = ''
    array.forEach(obj => {
        let scheduleCard = document.createElement("div")
        scheduleCard.classList.add("m-2")
        scheduleCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <p class="card-name">Event: ${obj.event}</p>
                    <p class="card-day">Date: ${obj.eventDate}</p>
                    <p class="card-time">Time: ${obj.eventTime}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getSchedule(${obj.id})" type="button" class="btn btn-primary"
                        data-bs-toggle="modal" data-bs-target="#schedule-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        scheduleContainer.append(scheduleCard);
    })
}

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

function backToPets(){
    let c = document.cookie.split(";");
    let count = 0
    while(count != 1){
        document.cookie = /^[^=]+/.exec(c[count])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
        count++
    }
}

const populateModal = (obj) =>{
    eventName.value = ""
    eventDate.value = ""
    eventName.value = obj.event
    eventDate.value = (obj.eventDate).concat("T"+obj.eventTime)
    updateEventBtn.setAttribute('data-schedule-id', obj.id)
}

getAllSchedules(petId);

submitForm.addEventListener("submit", handleSubmit)

updateEventBtn.addEventListener("click", (e)=>{
    let scheduleId = e.target.getAttribute('data-schedule-id')
    handleScheduleEdit(scheduleId);
})
