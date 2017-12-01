/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// create text box for input if user changes the security question combo box
function createTextBox(val){
    var inputBox = document.getElementById('tbSecurityQuestion');
    if(val !== "choose"){
        inputBox.className = 'isVisible';
        inputBox.style.color = 'black';
    }else{
        inputBox.className = 'notVisible';
    }
}

function validateForm(event){
            
    event.preventDefault(); // this will prevent the submit event.

    // Access variables
    var errorDiv = document.getElementById('errorMessage');

    var fullName = document.getElementById('tbFullName');
    var fullNameErrorDiv = document.getElementById('tbFullName_error');
    
    var email = document.getElementById('tbEmail');
    var emailErrorDiv = document.getElementById('tbEmail_error');
    
    var password = document.getElementById('tbPassword');
    var passwordErrorDiv = document.getElementById('tbPassword_error');
    
    var confirmPassword = document.getElementById('tbConfirmPassword');
    var confirmPasswordErrorDiv = document.getElementById('tbConfirmPassword_error');
    
    var DoB = document.getElementById('tbDoB');
    var DoBErrorDiv = document.getElementById('tbDoB_error');

    var securityQuestion = document.getElementById('tbSecurityQuestion');
    var securityQuestionErrorDiv = document.getElementById('tbSecurityQuestion_error');
    
    var errorMessage = '';
    var isError = false;

    // initally, assume no errors
    errorDiv.className = 'notVisible';

    fullNameErrorDiv.className = 'notVisible';
    fullName.style.background = 'white';

    emailErrorDiv.className = 'notVisible';
    email.style.background = 'white';

    passwordErrorDiv.className = 'notVisible';
    password.style.background = 'white';

    confirmPasswordErrorDiv.className = 'notVisible';
    confirmPassword.style.background = 'white';

    DoBErrorDiv.className = 'notVisible';
    DoB.style.background = 'white';

    securityQuestionErrorDiv.className = 'notVisible';
    securityQuestion.style.background = 'white';

    // Check if password = confirm password -- CHECK 1
    if(password.value !== confirmPassword.value){
        isError = true;
        errorMessage += 'Password and confirm password do not match.<br>';
        
        passwordErrorDiv.className = 'isVisibleAsterisk';
        confirmPasswordErrorDiv.className = 'isVisibleAsterisk';        
        password.style.background = 'yellow';
        confirmPassword.style.background = 'yellow';   
    }
    
    // check if fullname is one word -- CHECK 2
    // check if there is not at least one space in full name
    // make sure full name does not begin with a space
    if (fullName.value.split(" ").length - 1 < 1 || fullName.value[0] === ' '){
        isError = true;
        errorMessage += 'Full name is not valid.<br>';

        fullNameErrorDiv.className = 'isVisibleAsterisk';
        fullName.style.background = 'yellow';
    }

    // check if any input has a single quotation in it (') -- CHECK 3
    var quoteError = false;
    
    if(fullName.value.split("'").length - 1 > 0){
        quoteError = true;
        fullNameErrorDiv.className = 'isVisibleAsterisk';
        fullName.style.background = 'yellow';
    }
    if(email.value.split("'").length - 1 > 0){
        quoteError = true;
        emailErrorDiv.className = 'isVisibleAsterisk';
        email.style.background = 'yellow';
    }
    if(password.value.split("'").length - 1 > 0){
        quoteError = true;
        passwordErrorDiv.className = 'isVisibleAsterisk';
        password.style.background = 'yellow';
    }
    if(confirmPassword.value.split("'").length - 1 > 0){
        quoteError = true;
        confirmPasswordErrorDiv.className = 'isVisibleAsterisk';
        confirmPassword.style.background = 'yellow';
    }
    if(DoB.value.split("'").length - 1 > 0){
        quoteError = true;
        DoBErrorDiv.className = 'isVisibleAsterisk';
        DoB.style.background = 'yellow';
    }
    if(securityQuestion.value.split("'").length - 1 > 0){
        quoteError = true;
        securityQuestionErrorDiv.className = 'isVisibleAsterisk';
        securityQuestion.style.background = 'yellow';
    }
    if (quoteError){
        isError = true;
        errorMessage += 'Input has invalid characters(\').<br>';
    }
    
    // check password credentials -- CHECK 4
    // password must contain uppercase, lowercase, and digit
    var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])/;
    if (!re.test(password.value)){
        isError = true;
        errorMessage += 'Password must contain a lowercase, uppercase, and numerical value.<br>';
        
        passwordErrorDiv.className = 'isVisibleAsterisk';
        confirmPasswordErrorDiv.className = 'isVisibleAsterisk';
        
        password.style.background = 'yellow';
        confirmPassword.style.background = 'yellow';
    }
    
    // check that a security question was chosen
    var securityQuestionCombo = document.getElementById('securityQuestionCombo');
    var securityInput = document.getElementById('tbSecurityQuestion');
    if (securityQuestionCombo.value === "choose"){
        isError = true;
        errorMessage += 'Please choose a security question.<br>';
        securityQuestionErrorDiv.className = 'isVisibleAsterisk';
        securityQuestion.style.background = 'yellow';
    }else if(securityInput.value === '' || securityInput.value === securityInput.defaultValue){
        isError = true;
        errorMessage += 'Please answer security question.<br>';
        securityQuestionErrorDiv.className = 'isVisibleAsterisk';
        securityQuestion.style.background = 'yellow';
    }

    // display errors on form
    if (isError){
        errorDiv.innerHTML = errorMessage;
        errorDiv.className = 'isVisible';
        return false;
    }else{
         document.signupform.submit();// fire submit event
    }

    return true;
}