function hide(){
	var s=document.documentElement.style;
	s.cssText=s.cssText?'':'overflow:hidden;width:100%;height:100%';
}
hide();

var PI = 3.14159;
var canvas;
var ctx;

var hex_r = 10;
var field_x = 20;
var field_y = 20;

function DrawLine(x1,y1,x2,y2){
	ctx.beginPath();
	ctx.moveTo(x1,y1);
	ctx.lineTo(x2,y2);
	ctx.closePath();
	ctx.stroke();
}

function ToHexX(j, i){
	var add = i%2 == 0 ? 0 : 1;
	var space_x = Math.round(hex_r*0.5);//hex_r + Math.round(hex_r/2);
	var res = field_x + j*hex_r*2 + j*space_x + (hex_r+space_x/2)*add;
	return res;
}

function ToHexY(i){
	var space_y = Math.round(hex_r*0.8);
	var res = field_y + i*hex_r + i*space_y;
	return res;
}

function DrawCircle(x,y){
	x = ToHexX(x, y);
	y = ToHexY(y);
	ctx.beginPath();
	ctx.arc(x, y, 2, 0, Math.PI*2, false);
	ctx.stroke();
}

function DrawHex(x0, y0, r){
	x0 = ToHexX(x0, y0);
	y0 = ToHexY(y0);
	for(var i=0; i<6; i++){
		var x1 = x0 + Math.round( r * Math.cos( (i*60-90) * PI/180 ));
		var y1 = y0 - Math.round( r * Math.sin( (i*60-90) * PI/180 ));
		
		var x2 = x0 + Math.round( r * Math.cos( ((i+1)*60-90) * PI/180 ));
		var y2 = y0 - Math.round( r * Math.sin( ((i+1)*60-90) * PI/180 ));
		
		DrawLine(x1, y1, x2, y2);
		
	}
}

function FillHex(x0, y0, r, color){
	x0 = ToHexX(x0, y0);
	y0 = ToHexY(y0);
	r = r-1;
	ctx.fillStyle = color;
	ctx.beginPath();
	var first = true;
	for(var i=0; i<6; i++){
		if(first){
			var x1 = x0 + Math.round( r * Math.cos( (i*60-90) * PI/180 ));
			var y1 = y0 - Math.round( r * Math.sin( (i*60-90) * PI/180 ));
			ctx.moveTo(x1, y1);
			first = false;
		}
		
		var x2 = x0 + Math.round( r * Math.cos( ((i+1)*60-90) * PI/180 ));
		var y2 = y0 - Math.round( r * Math.sin( ((i+1)*60-90) * PI/180 ));
		ctx.lineTo(x2, y2);
		
		//DrawLine(x1, y1, x2, y2);
		
	}
	
	ctx.closePath();
	ctx.fill();
}

function thrd(){
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	//movement 
	
	for(var i=0; i<field_h; i++){
		
		for(var j=0; j<field_w; j++){
			DrawHex(j, i, hex_r);
		}
	}
	
	for(var i=0; i<filled.length; i++){
		FillHex(filled[i].x, filled[i].y, hex_r, '#444');
	}
	
	if(draw_movement){
		
		for(var i=0; i<members.length; i++){
			FillHex(members[i].x, members[i].y, hex_r, '#0f0');
		}
		DrawCircle(pivot.x, pivot.y);
	}
	
	if(draw_landing){
		
	}
	
	
	/*var pivot_x = 3;
	var pivot_y = 2;
	
	var hex_x = 1;
	var hex_y = 3;
	
	DrawCircle(ToHexX(pivot_x, pivot_y), ToHexY(pivot_y));
	FillHex(ToHexX(hex_x, hex_y), ToHexY(hex_y), hex_r, '#f00');
	
	var pivot_x_d = pivot_x + (pivot_y % 2 == 0 ? 0 : 0.5);
    var pivot_y_d = pivot_y * Math.sqrt(3) / 2;
    console.log("pivot x: " + pivot_x_d);
    console.log("pivot y: " + pivot_y_d);
 
    var hex_x_d = hex_x + ((hex_y % 2 == 0) ? 0 : 0.5);
    var hex_y_d = hex_y * Math.sqrt(3) / 2;
    console.log("hex x: " + hex_x_d);
    console.log("hex y: " + hex_y_d);
    ////////////////////////////////////////////////////////////////////
    var rlx = hex_x_d - pivot_x_d;
    var rly = hex_y_d - pivot_y_d;
    var alpha = 2*Math.PI/3;
    var rl2x = rlx * Math.cos(alpha) - rly * Math.sin(alpha);
    var rl2y = rlx * Math.sin(alpha) + rly * Math.cos(alpha);
    rl2x += pivot_x_d;
    rl2y += pivot_y_d;
    //////////////////////////////////////////////////////////////
    hex_y2 = 2 * rl2y / Math.sqrt(3);
    console.log("result y2: " + hex_y2);
    hex_y2 = Math.round(hex_y2);
    console.log("result y2: " + hex_y2);
    var hex_x2 = rl2x - ((hex_y2 % 2) == 0 ? 0 : 0.5);
    console.log("result x2: " + hex_x2);
    hex_x2 = Math.round(hex_x2);
	
	FillHex(ToHexX(hex_x2, hex_y2), ToHexY(hex_y2), hex_r, '#0f0');*/
}

function InitGraphic(){
	canvas = document.getElementById('paint_canvas');
	ctx = canvas.getContext('2d');
	
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	
	//thrd();
	window.setInterval("thrd();", 200);
}