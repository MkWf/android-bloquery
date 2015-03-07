Parse.Cloud.define("hasUserVotedAlready", function(request, response) {
	var query = new Parse.Query("Answer");
	query.equalTo("objectId", request.params.objectId);
	query.find({
		success: function(results){
			var array = results[0].get("usersVoted");
			for(int i = 0; i<array.length; i++){
				if(array.get[i] == request.params.userId){
					response.success(true);
				}else

				}
			}
			response.success(false);
		},
		error: function(){
			response.error("answer lookup failed")
		}
	})	
});
