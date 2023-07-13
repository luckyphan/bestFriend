//Cookie to read the logged in user's info
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1]
console.log(cookieArr)

//access DOM elements
const submitForm = document.getElementById("pet-form")
const petContainer = document.getElementById("pet-container")
//get the pet name,breed, medical
const petName = document.getElementById("pet-name")
const petBreed = document.getElementById("pet-breed")
const petMedHist = document.getElementById("pet-med-history")

//Modal Elements
let petNameBody = document.getElementById('pet-name-body')
let petBreedBody = document.getElementById('pet-breed-body')
let petMedHistBody = document.getElementById('pet-med-history-body')
//let petBody = document.getElementById(`pet-body`)
let updatePetBtn = document.getElementById('update-pet-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/pets/"

const handleSubmit = async (e) => {
    e.preventDefault()
    if(
        petName.value === '' ||
        petBreed.value === ''
    ){
         alert('Please fill out both the name and breed of your pet :)')
         return
     }
    let bodyObj = {
        name: petName.value,
        breed: petBreed.value,
        medical_history: petMedHist.value
    }
    console.log(bodyObj)
    await addPet(bodyObj,userId);
//    document.getElementById("pet-input").value = ''
}

async function addPet(obj,userId) {
    const response = await fetch(`${baseUrl}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getPets(userId);
    }
}

async function getPets(userId) {
    await fetch(`${baseUrl}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createPetCards(data))
        .catch(err => console.error(err))
}

async function handleDelete(petId){
    await fetch(baseUrl + petId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getPets(userId);
}

async function getPet(petId){
    await fetch(baseUrl + petId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handlePetEdit(petId){
    let bodyObj = {
        id: petId,
        name: petNameBody.value,
        breed: petBreedBody.value,
        medical_history: petMedHistBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getPets(userId);
}

const createPetCards = (array) => {
    petContainer.innerHTML = ''
    array.forEach(obj => {
        let petCard = document.createElement("div")
        petCard.classList.add("m-2")
        petCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <p class="card-name">Name: ${obj.name}</p>
                    <p class="card-breed">Breed: ${obj.breed}</p>
                    <p class="card-history">Medical history: ${obj.medical_history}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button class="btn btn-primary" onclick="viewSchedule(${obj.id})">Schedule</button>
                        <button onclick="getPet(${obj.id})" type="button" class="btn btn-primary"
                        data-bs-toggle="modal" data-bs-target="#pet-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        petContainer.append(petCard);
    })
}

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const populateModal = (obj) =>{
    petNameBody.value = ""
    petBreedBody.value = ""
    petMedHistBody.innerText = ""
    petNameBody.value= obj.name
    petBreedBody.value=obj.breed
    petMedHistBody.innerText = obj.medical_history
    updatePetBtn.setAttribute('data-pet-id', obj.id)
}

getPets(userId);

submitForm.addEventListener("submit", handleSubmit)

updatePetBtn.addEventListener("click", (e)=>{
    let petId = e.target.getAttribute('data-pet-id')
    handlePetEdit(petId);
})

function viewSchedule(petId){
document.cookie = `petId=${petId}`
window.location.href = `http://localhost:8080/planner.html`
}

