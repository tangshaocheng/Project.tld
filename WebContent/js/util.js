String.prototype.endWith=function(str){var reg=new RegExp(str+"$");return reg.test(this);}
String.prototype.startWith=function(str){return (this.substring(0,str.length)==str);}
function strTrim(value){
    if(value==null)return "";
    return value.replace(/(^\s*)|(\s*$)/g, ""); 
}
function isLetterNum (value){
	var LNName = /^[A-Za-z0-9]+$/;
	if(!LNName.test(value)){
		return false;
	}else return true;
}
function isNum(value){
   var regNum = /^[0-9]*$/;
	if(!regNum.test(value)){
		return false;
	}else return true;
}
//正整数
function isPlusNum(value){
   var regNum = /^[1-9]\d*$/;
	if(!regNum.test(value)){
		return false;
	}else return true;	
}
//数字非空
function isNumEmpty(value){
   var regNum = /^[0-9]+$/;
	if(!regNum.test(value)){
		return false;
	}else return true;
}
function isURL(urlString){
    regExp = /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i;
    if (urlString.match(regExp))return true;
    else return false;        
}
function isEmail(value){
      var regTextEmail = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
      if(!regTextEmail.test(value)){
		  return false;
	   }else return true;   
}
function isDate(dateStr){ 
    var datePat = /^(\d{4})(\-)(\d{1,2})(\-)(\d{1,2})$/;
    var matchArray = dateStr.match(datePat);
    if (matchArray == null) return false; 
    var month = matchArray[3];
    var day = matchArray[5]; 
    var year = matchArray[1]; 
    if (month < 1 || month > 12) return false; 
    if (day < 1 || day > 31) return false; 
    if ((month==4 || month==6 || month==9 || month==11) && day==31) return false; 
    if (month == 2){
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)); 
        if (day > 29 || (day==29 && !isleap)) return false; 
    } 
    return true;
}
function isPhone(value){
    var regTextfax = /^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/;
    if(!regTextfax.test(value)){
			return false;
	  }else return true;
}
function isMobile(value){
	var regTextfax =/^(13[4-9]|15[012789]|18[789]|147)(\d{8})$/;
    if(!regTextfax.test(value)){
			return false;
	  }else return true;
}
function isIP(s){
	  var patrn=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
	  if (!patrn.exec(s)) return false
	  return true
} 
function inputgtzero(obj){
	   if(obj.value=='')return;
	   if(isNaN(obj.value)){
		   obj.value="";
		   return;
	   }
	    obj.value=obj.value.replace(/[^\d]/g,'');
}
function trims(str)      
{          
    var t = str.replace(/(^\s*)|(\s*$)/g, "");   
    t =  t.replace(/(^　*)|(　*$)/g, ""); 
    return t.replace(/(^\s*)|(\s*$)/g, "");     
} 
function formateDate(str,pattern){
	   var tDate=new Date(str);
	   var ftstr="";
	   if(pattern.indexOf("YYYY")!=-1){
	       ftstr+=tDate.getYear();
	   }
	   if(pattern.indexOf("YYYY")==-1&&pattern.indexOf("YY")!=-1){
	       ftstr+=tDate.getYear().subString(2,4);
	   }
	   if(pattern.indexOf("MM")!=-1){
		   if((tDate.getMonth()+1)<10) ftstr+="-0"+(tDate.getMonth()+1);
	       else ftstr+="-"+(tDate.getMonth()+1);
	   }
	   if(pattern.indexOf("DD")!=-1){
		   if(tDate.getDate()<10) ftstr+="-0"+tDate.getDate();
	       else ftstr+="-"+tDate.getDate();
	   }
	   if(pattern.indexOf("HH")!=-1){
		   if(tDate.getHours()<10) ftstr+=" 0"+tDate.getHours();
	       else ftstr+=" "+tDate.getHours();
	   }
	   if(pattern.indexOf("mm")!=-1){
		   if(tDate.getMinutes()<10) ftstr+=":0"+tDate.getMinutes();
	       else ftstr+=":"+tDate.getMinutes();
	   }
	   if(pattern.indexOf("SS")!=-1||pattern.indexOf("ss")!=-1){
	       if(tDate.getSeconds()<10) ftstr+=":0"+tDate.getSeconds();
		   else ftstr+=":"+tDate.getSeconds();
	   }
	   return ftstr;
}
function closeMyPop(){
	$("#myPopTip").css("display","none");
	$("#myDivCover").css("display","none");
}
function getDateYYYYMMDD(str){
	   var tDate=new Date(str);
	   return tDate.getYear()+"-"+(tDate.getMonth()+1)+"-"+tDate.getDate();
}
function getTimestamp(){
	var tDate = new Date();
	var year = tDate.getFullYear();    //获取完整的年份(4位,1970-????)
	var month = tDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
	if(month<10) month = '0' + month;
	var day = tDate.getDate();        //获取当前日(1-31)
	if(day<10) day = '0' + day;
	var hour = tDate.getHours();       //获取当前小时数(0-23)
	if(hour<10) hour = '0' + hour;
	var min = tDate.getMinutes();     //获取当前分钟数(0-59)
	if(min<10) min = '0' + min;
	var second = tDate.getSeconds();     //获取当前秒数(0-59)
	if(second<10) second = '0' + second;
	var mills = tDate.getMilliseconds();    //获取当前毫秒数(0-999)
	if(mills<10) mills = '00' + mills;
	else if(mills<100) mills = '0' + mills;
	var timestamp = year + month + day + hour + min + second + mills;
	return timestamp;
}
function getNowDate(){
    var tDate=new Date();
    return tDate.getYear()+"-"+(tDate.getMonth()+1)+"-"+tDate.getDate();
}
function getCheckedRadio(radioName){
	var rs =document.getElementsByName(radioName);
	for(var i=0;i<rs.length;i++){
		if(rs[i].checked)return rs[i].value;
	}
}
//四舍五入保留2位小数，如：2，会在2后面补上00.即2.00
function toDecimal2(x) {
    var f = parseFloat(x); 
    if (isNaN(f)) {    
        return false;    
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}
function toInt(x){
	var i = parseInt(x);
    if(isNaN(i)){
        return false;
    }
    return i;
}
function checkInputFloat(oInput){
	if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,'')){
		oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' : oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
	}
}
function checkInputInt(oInput){
	if(oInput.value.length == 1){
		oInput.value = oInput.value.replace(/[^1-9]/g,'');
	}else{
		oInput.value = oInput.value.replace(/\D/g,'');
	}
}
function checkInputInt0(oInput){
	if(oInput.value.length == 1){
		oInput.value = oInput.value.replace(/[^0-9]/g,'');
	}else{
		oInput.value = oInput.value.replace(/\D/g,'');
	}
}
function drawImage(ImgD,width,height){
	var image=new Image(); 
	var iwidth = width; //允许图片的最大宽度 
	var iheight = height; //允许图片的最大高度 
	image.src=ImgD.src; 
	if(image.width>0 && image.height>0){ 
		flag=true; 
		if(image.width/image.height>= iwidth/iheight){ 
			if(image.width>iwidth){ 
				ImgD.width=iwidth; 
				ImgD.height=(image.height*iwidth)/image.width; 
			}else{ 
				ImgD.width=image.width; 
				ImgD.height=image.height; 
			} 
		} else{ 
			if(image.height>iheight){ 
				ImgD.height=iheight; 
				ImgD.width=(image.width*iheight)/image.height; 
			}else{ 
				ImgD.width=image.width; 
				ImgD.height=image.height; 
			} 
		} 
	} 

}
//文件格式检查 
function checkUploadFile(tagName){
	   if($("#"+tagName).val()!=""){
	       var sufix=$("#"+tagName).val().lastIndexOf(".");
	       var accept=$("#"+tagName).attr("accept");
	       if(sufix==-1){
	            alert("只支持"+accept+"文件");
	            return false;
	           }
	       sufix=$("#"+tagName).val().substring(sufix+1,$("#"+tagName).val().length).toLowerCase();
	       
	       if(accept.indexOf(sufix)==-1){
		    	   alert("只支持"+accept+"文件");
		           return false;
	           }
		   }
	   return true;
}
function getBytesLength(str) {   
	   return str.replace(/[^\x00-\xff]/g, 'xx').length;   
}
function inputGtLength(objId,maxLength,alt){
	   if(getBytesLength($("#"+objId).val())>maxLength*1){
		   alert(""+alt+",中文按照双字节计算");$("#"+objId).focus();return false;
	   }
	   return true;
}
function getPara(){
	return $(":input").serializeArray();
}
function ajaxPost(_url,_method, _para){
	  if(_para==null)_para=	$(":input").serializeArray();
	  $.ajax({
		  type:"post",
		  url:_url,
		  data:_para,
		  success:function (data){
		     if(data!=null&&data[0]!=null){
		    	if(data[0]['info']=='sqlEx'){
		    		 alert("数据库操作出错");
		    	 }else if(data[0]['info']=='fileEx'){
					alert("文件读取出错");		    		 
				}else if(data[0]['info']=='otherEx'){
					alert("其他不明错误");
				}else{
					_method.call(this,data);
				}
		     }else{
		    	 _method.call(this,data);
		     }
	      },
		  error:ajaxError,
		  timeout:120000,
		  dataType:"json"
	  });
}
function ajaxError(){
	alert("操作出错");
}
function divLogin(){
    showDivDetail(contextPath+"/toDivLogin.do",300,200,10,10,"登录");
}

/**
 * 创建MAP对象
 */
Array.prototype.remove = function(s){
    for(var i = 0; i < this.length; i++){
        if (s == this[i]){
            this.splice(i, 1);
        }
    }
}

/**
 * Simple Map
 *
 *
 * var m = new Map();
 * m.put('key','value');
 * ...
 * var s = "";
 * m.each(function(key,value,index){
 *      s += index+":"+ key+"="+value+"/n";
 * });
 * alert(s);
 *
 * @author dewitt
 * @date 2008-05-24
 */
function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value){
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key){
        return this.data[key];
    };

    /**
     * 判断Map是否包含某键
     * @param {String} key
     * @return {Boolean} value
     */
    this.contain = function(key){
		if(this.data[key] == null){
			return false;
		}
		return true;
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key){
        this.keys.remove(key);
        this.data[key] = null;
    };

    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };

    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++){
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function(){
        return this.keys.length == 0;
    };

    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };

    /**
     * 重写toString
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}
