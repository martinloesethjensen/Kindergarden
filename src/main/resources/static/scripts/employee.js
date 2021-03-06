function printer(id) {
    $("#formid").attr("action", "details?id=" + id).submit();
}

$(document).ready(function () {
    $("#openCreateNewEmployee").click(function () {
        $("#overlay, #createNewEmployee").show();
    });

    if($("#success").text() != "") {
        $("#success").css({"margin-left": "-"+($("#success").outerWidth()/2)+"px"});
        $("#success").fadeIn(500).delay(3000).fadeOut(500);
    }

    $("#cancelEmployeeCreate, #overlay").click(function () {
        $("#overlay, .popup-formular").hide();
    });

    $(".openEditEmployee").click(function () {
        $("#overlay, #editEmployee").show();
    });

    $("#cancelEmployeeEdit, #overlay").click(function () {
        $("#overlay, .popup-formular").hide();
    });

    $(document).keyup(function (e) {
        if (e.keyCode == 27) {
            $("#overlay, .popup-formular").hide();
            $(".popup-formular input[type=text],.popup-formular input[type=number]").val("");
        }
    });

    //Validation for new employee
    $(document).on('click', 'form input[name=saveEmployee]', function(e) {
        var isValid = true;

        if($("#employeeUsername").val().length == 0) {
            $(".employeeUsernameFail").show();
            isValid = false;
        } else {
            $(".employeeUsernameFail").hide();
        }

        if($("#employeePassword").val().length ==  0) {
            $(".employeePasswordFail").show();
            isValid = false;
        } else {
            $(".employeePasswordFail").hide();
        }

        if($("#employeeFirstName").val().length == 0) {
            $(".employeeFirstNameFail").show();
            isValid = false;
        } else {
            $(".employeeFirstNameFail").hide();
        }

        if($("#employeeLastName").val().length == 0) {
            $(".employeeLastNameFail").show();
            isValid = false;
        } else {
            $(".employeeLastNameFail").hide();
        }

        if($("#employeeAddress").val().length == 0) {
            $(".employeeStreetNameFail").show();
            isValid = false;
        } else {
            $(".employeeStreetNameFail").hide();
        }

        if($("#employeeAddressCity").val().length == 0) {
            $(".employeeCityFail").show();
            isValid = false;
        } else {
            $(".employeeCityFail").hide();
        }

        if($("#employeeAddressPostalCode").val() == 0 || $("#employeeAddressPostalCode").val().length != 4) {
            $(".employeePostalCodeFail").show();
            isValid = false;
        } else {
            $(".employeePostalCodeFail").hide();
        }

        if($("#employeePhoneNumber").val() == 0 || $("#employeePhoneNumber").val().length != 8) {
            $(".employeePhoneNumberFail").show();
            isValid = false;
        } else {
            $(".employeePhoneNumberFail").hide();
        }

        var email = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        if($("#employeeEmail").val().length == 0 || !($("#employeeEmail").val().match(email))) {
            $(".employeeEmailFail").show();
            isValid = false;
        } else {
            $(".employeeEmailFail").hide();
        }

        e.preventDefault();

        $.ajax({
            type : "POST",
            url : "/checkUsername",
            data : {
                "username" : $("#employeeUsername").val()
            }, success: function(data){
                if(data) {
                    $(".employeeUsernameTakenFail").show();
                    isValid = false;
                } else {
                    $(".employeeUsernameTakenFail").hide();
                }

                if(isValid) {
                    $("#createEmployeeForm").unbind('submit').submit();
                }
            }
        })
    });

    //Validation for edit employee
    $(document).on('click', 'form input[name=saveEditEmployee]', function(e) {
        var isValid = true;

        if($("#editEmployeeFirstName").val().length == 0) {
            $(".employeeFirstNameFail").show();
            isValid = false;
        } else {
            $(".employeeFirstNameFail").hide();
        }

        if($("#editEmployeeLastName").val().length == 0) {
            $(".employeeLastNameFail").show();
            isValid = false;
        } else {
            $(".employeeLastNameFail").hide();
        }

        if($("#editEmployeeAddress").val().length == 0) {
            $(".employeeStreetNameFail").show();
            isValid = false;
        } else {
            $(".employeeStreetNameFail").hide();
        }

        if($("#editEmployeeAddressCity").val().length == 0) {
            $(".employeeCityFail").show();
            isValid = false;
        } else {
            $(".employeeCityFail").hide();
        }

        if($("#editEmployeeAddressPostalCode").val() == 0 || $("#editEmployeeAddressPostalCode").val().length != 4) {
            $(".employeePostalCodeFail").show();
            isValid = false;
        } else {
            $(".employeePostalCodeFail").hide();
        }

        if($("#editEmployeePhoneNumber").val() == 0 || $("#editEmployeePhoneNumber").val().length != 8) {
            $(".employeePhoneNumberFail").show();
            isValid = false;
        } else {
            $(".employeePhoneNumberFail").hide();
        }

        var email = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        if($("#editEmployeeEmail").val().length == 0 || !($("#editEmployeeEmail").val().match(email))) {
            $(".employeeEmailFail").show();
            isValid = false;
        } else {
            $(".employeeEmailFail").hide();
        }

        if(!isValid){e.preventDefault();}

    });
});