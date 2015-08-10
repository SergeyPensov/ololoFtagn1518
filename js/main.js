var problem = '';
//p6
//var problem = '{"height":10,"width":10,"sourceSeeds":[0,13120,18588,31026,7610,25460,23256,19086,24334,22079,9816,8466,3703,13185,26906,16903,24524,9536,11993,21728,2860,13859,21458,15379,10919,7082,26708,8123,18093,26670,16650,1519,15671,24732,16393,5343,28599,29169,8856,23220,25536,629,24513,14118,17013,6839,25499,17114,25267,8780],"units":[{"members":[{"x":0,"y":0}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":2,"y":0}],"pivot":{"x":1,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":0,"y":1}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":1,"y":1}],"pivot":{"x":1,"y":0}}],"id":6,"filled":[],"sourceLength":150}';

//p3
//var problem = '{"height":20,"width":30,"sourceSeeds":[0,29060,6876,31960,6094],"units":[{"members":[{"x":0,"y":0},{"x":2,"y":0}],"pivot":{"x":1,"y":0}},{"members":[{"x":1,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":2,"y":0},{"x":1,"y":0},{"x":0,"y":1}],"pivot":{"x":1,"y":0}},{"members":[{"x":1,"y":1},{"x":1,"y":0},{"x":0,"y":1}],"pivot":{"x":0,"y":0}},{"members":[{"x":2,"y":0},{"x":1,"y":1},{"x":1,"y":2},{"x":0,"y":3}],"pivot":{"x":1,"y":1}},{"members":[{"x":2,"y":0},{"x":1,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":1,"y":1}},{"members":[{"x":1,"y":1},{"x":1,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":1,"y":0},{"x":1,"y":1},{"x":1,"y":2},{"x":0,"y":3}],"pivot":{"x":0,"y":1}},{"members":[{"x":2,"y":0},{"x":1,"y":1},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":1,"y":1}},{"members":[{"x":2,"y":1},{"x":2,"y":0},{"x":1,"y":0},{"x":0,"y":1}],"pivot":{"x":1,"y":0}},{"members":[{"x":1,"y":1},{"x":2,"y":0},{"x":1,"y":0},{"x":0,"y":1}],"pivot":{"x":1,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":1},{"x":1,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":0,"y":1},{"x":1,"y":1},{"x":3,"y":0},{"x":2,"y":0}],"pivot":{"x":1,"y":0}}],"id":3,"filled":[{"x":2,"y":5},{"x":3,"y":5},{"x":4,"y":5},{"x":5,"y":5},{"x":11,"y":5},{"x":16,"y":5},{"x":17,"y":5},{"x":18,"y":5},{"x":19,"y":5},{"x":25,"y":5},{"x":4,"y":6},{"x":11,"y":6},{"x":18,"y":6},{"x":25,"y":6},{"x":4,"y":7},{"x":11,"y":7},{"x":18,"y":7},{"x":25,"y":7},{"x":4,"y":8},{"x":11,"y":8},{"x":18,"y":8},{"x":25,"y":8},{"x":4,"y":9},{"x":7,"y":9},{"x":8,"y":9},{"x":11,"y":9},{"x":18,"y":9},{"x":21,"y":9},{"x":22,"y":9},{"x":25,"y":9},{"x":4,"y":10},{"x":7,"y":10},{"x":9,"y":10},{"x":18,"y":10},{"x":21,"y":10},{"x":23,"y":10},{"x":2,"y":11},{"x":3,"y":11},{"x":4,"y":11},{"x":5,"y":11},{"x":7,"y":11},{"x":8,"y":11},{"x":9,"y":11},{"x":11,"y":11},{"x":16,"y":11},{"x":17,"y":11},{"x":18,"y":11},{"x":19,"y":11},{"x":21,"y":11},{"x":22,"y":11},{"x":23,"y":11},{"x":25,"y":11}],"sourceLength":100}';
//p1
//var problem = '{"height":15,"width":15,"sourceSeeds":[0],"units":[{"members":[{"x":0,"y":0}],"pivot":{"x":0,"y":0}}],"id":1,"filled":[{"x":2,"y":4},{"x":3,"y":4},{"x":4,"y":4},{"x":5,"y":4},{"x":6,"y":4},{"x":11,"y":4},{"x":2,"y":5},{"x":8,"y":5},{"x":11,"y":5},{"x":2,"y":6},{"x":11,"y":6},{"x":2,"y":7},{"x":3,"y":7},{"x":4,"y":7},{"x":8,"y":7},{"x":11,"y":7},{"x":2,"y":8},{"x":9,"y":8},{"x":11,"y":8},{"x":2,"y":9},{"x":8,"y":9},{"x":2,"y":10},{"x":3,"y":10},{"x":4,"y":10},{"x":5,"y":10},{"x":6,"y":10},{"x":9,"y":10},{"x":11,"y":10}],"sourceLength":100}';
//p0
//var problem = '{"height":10,"width":10,"sourceSeeds":[0],"units":[{"members":[{"x":0,"y":0}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":2,"y":0}],"pivot":{"x":1,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":2,"y":0},{"x":0,"y":1},{"x":2,"y":2}],"pivot":{"x":1,"y":1}},{"members":[{"x":0,"y":0},{"x":1,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":0,"y":0},{"x":1,"y":0}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0}],"pivot":{"x":1,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":1}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":1}],"pivot":{"x":0,"y":1}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":2,"y":0}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":2,"y":0}],"pivot":{"x":1,"y":0}},{"members":[{"x":0,"y":0},{"x":1,"y":0},{"x":2,"y":0}],"pivot":{"x":2,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":0}},{"members":[{"x":0,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":1}},{"members":[{"x":0,"y":0},{"x":0,"y":1},{"x":0,"y":2}],"pivot":{"x":0,"y":2}},{"members":[{"x":1,"y":0},{"x":0,"y":1},{"x":1,"y":2}],"pivot":{"x":1,"y":0}},{"members":[{"x":1,"y":0},{"x":0,"y":1},{"x":1,"y":2}],"pivot":{"x":1,"y":1}},{"members":[{"x":1,"y":0},{"x":0,"y":1},{"x":1,"y":2}],"pivot":{"x":1,"y":2}}],"id":0,"filled":[],"sourceLength":100}';


var field_w = 0;
var field_h = 0;

var draw_movement = false;
var draw_landing = false;

//var pivot = { x: 2, y: 3 };
//var filled = [{ x: 2, y: 19}, { x: 3, y: 19}, { x: 3, y: 18}, { x: 6, y: 19}, { x: 5, y: 19} ];
//var members = [{ x: 3, y: 3}, { x: 1, y: 3} ];

var filled = [];
var units = [];

//for landing
var landed_units = [];

//for movement
var current_pivot = 0;
var pivot = undefined;
var members = [];
var commands = "";
var seeds = [];
var generator = undefined;
var unit_width = 0;
//mW - left
//mE - right
//mSW
//mSE
//rC
//rCC

/**********************************************CONTROLS****************************************************/
function SpawnPivot(){
	current_pivot = generator.generate()%units.length;
	pivot = units[current_pivot].pivot;
	members = units[current_pivot].members;
	
	var pivot_max_x = pivot.x;
	var pivot_min_x = pivot.x;
	for(var i=0; i<members.length; i++){
		pivot_max_x = members[i].x > pivot_max_x ? members[i].x : pivot_max_x;
		pivot_min_x = members[i].x < pivot_min_x ? members[i].x : pivot_min_x;
	}
	unit_width = Math.abs(pivot_max_x - pivot_min_x) + 1;
	
	spawn_x = Math.floor((field_w - unit_width)/2);
	var new_unit = MoveUnit(pivot, members, spawn_x, 0);
	pivot = new_unit.pivot;
	members = new_unit.members;
}

function ClearFilledLines(){
	var field = [];
	for(var i=0; i<field_h; i++){
		var tmp = [];
		for(var j=0; j<field_w; j++){
			tmp.push(0);
		}
		field.push(tmp);
	}
	
	
	for(var i=0; i<filled.length; i++){
		field[filled[i].y][filled[i].x] = 1;
	}
	console.log(field);
	
	while(true){
		var line_num = -1;
		for(var i=0; i<field_h; i++){
			line_filled = true;
			for(var j=0; j<field_w; j++){
				if(field[i][j] != 1){
					line_filled = false;
					break;
				}
			}
			
			if(line_filled){
				line_num = i;
				break;
			}
		}
		
		if(line_num != -1){
			
			for(var i=line_num; i>=0; i--){ // shift down all field from line_num
				for(var j=0; j<field_w; j++){
					if(i == 0){
						field[i][j] = 0;
					} else {
						field[i][j] = field[i-1][j];
					}
				}
			}
			
			for(var i=0; i<filled.length; i++){
				if(filled[i].y == line_num){
					filled.splice(i, 1);
					i--;
				}
			}
			
			for(var i=0; i<filled.length; i++){
				if(filled[i].y < line_num){
					filled[i].y += 1;
				}
			}
			
			
			
		} else {
			break;
		}
		
	}
	
	
}

function AddToFilled(members){
	for(var i=0; i<members.length; i++){
		filled.push(members[i]);
	}
}

function CheckUnitLanded(pivot, members){
	var res = false;
	for(var i=0; i<members.length; i++){
		if(res){ break; }
		
		if(members[i].x >= field_w || members[i].x < 0 || members[i].y >= field_h || members[i].y < 0){
			res = true;
			break;
		}
		
		for(var j=0; j<filled.length; j++){
			if((members[i].x == filled[j].x) && (members[i].y == filled[j].y)){
				res = true;
				break;
			}
		}
	}
	return res;
}

function MoveUnit(pivot, members, move_x, move_y){
	var new_members = [];
	var add_x = 0;
	if(move_y > 0){
		add_x = ((pivot.y%2) != 0) ? 1 : 0;
	}
	var new_pivot = {x : pivot.x + move_x + add_x, y : pivot.y + move_y};
	
	for(var i=0; i<members.length; i++){
		var add_x = 0;
		if(move_y > 0){
			add_x = ((members[i].y%2) != 0) ? 1 : 0;
		}
		new_members.push({x : members[i].x + move_x + add_x, y : members[i].y + move_y});
	}
	return {
		members : new_members,
		pivot : new_pivot
	};
}

function rotateUnit(pivot, members, rotate){
	var new_members = [];
	var new_pivot = {x : pivot.x, y : pivot.y};
	
	var pivot_x_d = pivot.x + (pivot.y % 2 == 0 ? 0 : 0.5);
    var pivot_y_d = pivot.y * Math.sqrt(3) / 2;
	
	for(var i=0; i<members.length; i++){
		var hex_x_d = members[i].x + ((members[i].y % 2 == 0) ? 0 : 0.5);
		var hex_y_d = members[i].y * Math.sqrt(3) / 2;
		
		var rlx = hex_x_d - pivot_x_d;
		var rly = hex_y_d - pivot_y_d;
		var alpha = Math.PI/3;
		var rl2x = rlx * Math.cos(alpha) - rly * Math.sin(alpha)*rotate;
		var rl2y = rlx * Math.sin(alpha)*rotate + rly * Math.cos(alpha);
		rl2x += pivot_x_d;
		rl2y += pivot_y_d;
		
		hex_y2 = 2 * rl2y / Math.sqrt(3);
		hex_y2 = Math.round(hex_y2);
		var hex_x2 = rl2x - ((hex_y2 % 2) == 0 ? 0 : 0.5);
		hex_x2 = Math.round(hex_x2);
		
		new_members.push({x : hex_x2, y : hex_y2});
	}
	
	return {
		members : new_members,
		pivot : new_pivot
	};
}

function DoStep(){
	
	var new_unit = undefined;
	var moved = false;
	var command = "";
	while(commands.length){
		command = commands[0];
		commands = commands.substring(1);
		
		if(("p'!.03").indexOf(command) != -1){ //left
			console.log("left");
			new_unit = MoveUnit(pivot, members, -1, 0);
			moved = true;
			break;
		} else if(("bcefy2").indexOf(command) != -1){ //right
			console.log("right");
			new_unit = MoveUnit(pivot, members, 1, 0);
			moved = true;
			break;
		} else if(("aghij4").indexOf(command) != -1){ //down-left
			console.log("down left");
			new_unit = MoveUnit(pivot, members, -1, 1);
			moved = true;
			break;
		} else if(("lmno 5").indexOf(command) != -1){ //down-right
			console.log("down right");
			new_unit = MoveUnit(pivot, members, 0, 1);
			moved = true;
			break;
		} else if(("dqrvz1").indexOf(command) != -1){ //rotate clockwise
			console.log("rotate clockwise");
			new_unit = rotateUnit(pivot, members, 1);
			moved = true;
			break;
		} else if(("kstuwx").indexOf(command) != -1){ //rotate counter-clockwise
			console.log("rotate counter-clockwise");
			new_unit = rotateUnit(pivot, members, -1);
			moved = true;
			break;
		} 
	}
	
	if(moved){
		if(CheckUnitLanded(new_unit.pivot, new_unit.members)){
			//commands = command + commands;//get command back
			
			AddToFilled(members);
			ClearFilledLines();
			SpawnPivot();
		} else {
			pivot = new_unit.pivot;
			members = new_unit.members;
		}
	}
	
	
	
	
	/*if(commands.length>0){
		c = commands.shift();
		if(c == "mW"){
			
		} else if(c == "mE"){
			
		} else if(c == "mSW"){
			
		} else if(c == "mSE"){
			
		} else if(c == "rC"){
			
		} else if(c == "rCC"){
			
		}
	}*/
	RefreshCommands();
}

function RefreshCommands(){
	/*var text = "";
	for(var i=0; i<commands.length; i++){
		var c = commands[i];
		text += c + "\r\n";
	}*/
	$("#command_list").val(commands);
}

function SendProblem(){
	return function(){
		problem = $("#problem_text").val();
		var p = JSON.parse(problem);
		console.log(p);
		
		field_w = p.width;
		field_h = p.height;
		
		
		
		
		units = p.units;
		seeds = p.sourceSeeds;
		filled = p.filled;
		var seed_num = $("#seed_num").val();
		generator = RandomGenerator(seeds[seed_num]);
		
		commands = $("#command_list").val();
		
		SpawnPivot();
		
		
		//calc spawn_point
		
		RefreshCommands();
		draw_movement = true;
		draw_landing = false;
		
		
		/*var myJSObject = {data : 1}
		$.ajax
		({
			type : 'POST',
			url : 'test.php',
			data : JSON.stringify(myJSObject),
			contentType : 'application/json',
			
			success: function (data) {
				console.log( data ); 
			}
		});*/
		
		/*$.post( "test.php", { param : "test" }, function( data ) {
			console.log( data );
		}, "json");*/
	}
}

function OnNextBtnClick(){
	return function(){
		DoStep();
	}
}

function OnTimerBtnClick(){
	return function(){
		window.setInterval("DoStep();", 30);
	}
}

/**********************************************INIT****************************************************/
$(function() {
	InitGraphic();
	
	$("#problem_btn").click(SendProblem());
	$("#next_btn").click(OnNextBtnClick());
	$("#timer_btn").click(OnTimerBtnClick());
});