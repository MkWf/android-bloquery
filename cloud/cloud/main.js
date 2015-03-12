Parse.Cloud.define("setUserVote", function(request, response) {
	var query = new Parse.Query("Answer");
	var votes;
	var jsonObject;
	var removed = 0;
	
	query.equalTo("objectId", request.params.objectId);
	query.find({
		success: function(results){
			var array = results[0].get("usersVoted");
			if(array.length != 0){
				for(var i = 0; i<array.length; i++){
					if(array[i] == request.params.userId){
						votes = results[0].get("votes");
						votes = votes - 1;
						results[0].set("votes", votes);
						array.splice(i, 1);
						removed = 1;
						break;
					}
				}
			}
			if(removed != 1){
				votes = results[0].get("votes");
				votes = votes + 1;
				results[0].set("votes", votes);
				array.push(request.params.userId);
			}
			
			jsonObject = {"result": votes};
			
			Parse.Object.saveAll(results, {
				success: function(list) {
					response.success(jsonObject);
				},
				error: function(error){
					
				}
            })
			
		},
		error: function(){
			response.error("answer lookup failed")
		}
	})	
});
