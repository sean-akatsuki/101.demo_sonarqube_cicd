function ajaxCaller(service_name){

    var url = '/';
    if(service_name=='database'){
        url = url + 'checkdb';
    }else if(service_name=='jenkins'){
        url = url + 'checkjenkins';
    }else{
        url = url + 'checksonar';
    }

    
    $.ajax({
        url : url,
        type : 'GET',
        data : {},
        dataType: 'text',
        success : function(data) {
            if(service_name=='database'){
                if (data=="1"){
                    $("#database-rs").addClass("error");
                }else{
                    $("#database-rs").removeClass("error");
                }
            }else if(service_name=='jenkins'){
                if (data=="1"){
                    $("#jenkins-rs").addClass("error");
                }else{
                    $("#jenkins-rs").removeClass("error");
                }
            }else{
                if (data=="1"){
                    $("#sonarqube-rs").addClass("error");
                }else{
                    $("#sonarqube-rs").removeClass("error");
                }
            }
            
        }
    });

}