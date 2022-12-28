const data = require('./json/data.json');
const count = require('./json/count.json');

const Constants = require('../../helper/constant')

module.exports = {
    getAppointments: data,
    getAllDistinctPatients: getAllDistinctPatients,
    getAllAppointmentSpecialist: getAllAppointmentSpecialist,
    findBySpecialityId: findBySpecialityId,
    getAllTodayAppointment: getAllTodayAppointment,
    getAllSupTodayAppointment: getAllSupTodayAppointment,
    createAppointment: createAppointment,
    getAllActivedAppoitment: getAllActivedAppoitment,
    getAllArchivedAppoitment: getAllArchivedAppoitment,
    deleteAppoitment: deleteAppoitment,
    findAppointmentById: findAppointmentById,
    getAllByIdAppoitment: getAllByIdAppoitment,
    getByspecialistIdAppoitment: getByspecialistIdAppoitment,
    updateSpecialistState: updateSpecialistState,
    getCountAllAppointment: getCountAllAppointment
};

function getCountAllAppointment(appointments, id) {
    return count.count
}

function updateSpecialistState(appointments, id, state) {
    let Appointment = findAppointmentById(appointments, id)
    Appointment.state = state;
    return Appointment;
}

var Distinctpatient = [];

/**
 * 
 * @param {Array} appointments 
 * @returns 
 */
function getAllDistinctPatients(appointments) {
    var i = 0
    for (i = 0; i < appointments.length; i++) {
        if (isDistinct(appointments[i].patientPhone)) {
            Distinctpatient.push(appointments[i])
        }
    }
    return Distinctpatient;
}

function isDistinct(numero) {
    var i = 0
    for (i = 0; i < Distinctpatient.length; i++) {
        if (Distinctpatient[i].patientPhone == numero) {
            return false;
        }
    }
    return true;
}

function getAllAppointmentSpecialist(appointments, id) {
    var i = 0;
    var AppointmentSpcialist = []
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].specialistId.specialistId == id) {
            AppointmentSpcialist.push(appointments[i]);
        }
    }
    return AppointmentSpcialist;
}

function findBySpecialityId(appointments, id) {
    var i = 0
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].specialistId.specialistId == id) {
            return true
        }
    }
    return false
}

function getByspecialistIdAppoitment(appointments, id) {
    var i = 0
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].specialistId.specialistId == id) {
            return appointments[i]
        }
    }
    return null
}

function getAllTodayAppointment(appointments, id) {
    var AppointmentSpcialist = []
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].specialistId.specialistId == id) {
            if (new Date(appointments[i].appointmentDate) == new Date()) {
                AppointmentSpcialist.push(appointments[i]);
            }
        }
    }
    return AppointmentSpcialist
}

function getAllSupTodayAppointment(appointments, id) {
    var AppointmentSpcialist = []
    console.log(new Date());
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].specialistId.specialistId == id) {
            if (new Date(appointments[i].appointmentDate) >= new Date()) {
                AppointmentSpcialist.push(appointments[i]);
            }
        }
    }
    return AppointmentSpcialist
}

function createAppointment(appointment) {
    return appointment;
}

function getAllActivedAppoitment(appointments) {
    var i = 0;
    var Appointment = []
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].status == Constants.STATE_ACTIVATED) {
            Appointment.push(appointments[i]);
        }
    }
    return Appointment;
}

function getAllArchivedAppoitment(appointments) {
    var i = 0;
    var Appointment = []
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].status == Constants.STATE_ARCHIVE) {
            Appointment.push(appointments[i]);
        }
    }
    return Appointment;
}

function findAppointmentById(appointments, id) {
    var i = 0
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].appointmentId == id) {
            return appointments[i]
        }
    }
    return null
}

function deleteAppoitment(appointments, id) {
    return findAppointmentById(appointments, id)
}

function getAllByIdAppoitment(appointments, id) {
    var i = 0
    for (let i = 0; i < appointments.length; i++) {
        if (appointments[i].appointmentId == id) {
            return appointments[i]
        }
    }
    return null
}