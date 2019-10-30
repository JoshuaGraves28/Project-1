var Project1 = ( function() {

    return {

        init: function() {
            
            $("#version").html( "jQuery Version: " + $().jquery );

        },
        
        submitSearchForm: function() {

            if ( $("#search").val() === "" ) {

                alert("You must enter a search parameter!  Please try again.");
                return false;

            }

            $.ajax({

                url: 'registration',
                method: 'GET',
                data: $('#searchform').serialize(),

                success: function(response) {

                    $("#resultsarea").html(response);

                }

            });

            return false;

        },
        submitRegistrationForm: function() {
            
            var that = this;
            
            $.ajax({

                url: 'registration',
                method: 'POST',
                data: $('#registration').serialize(),
                dataType: 'json',

                success: function(response) {
                    that.successful_register(response);
                }
            });  
        },
                
        successful_register: function(result){
            var output = "<p>Congradulations, You have registered as: "+ result["displayname"]+ "</p>";
            var output2= "<p>Your registration code is: "+ result["code"];
            var together= output + output2;
            $("#resultsarea").html(together);
        }
        

    };

}());