local spr = app.activeSprite
local dlg = Dialog("What to do?")
	:number{ id="cx", label="Center X", text=""..(spr.width/2) }
	:number{ id="cy", label="Center Y", text=""..(spr.height/2) }
	:button{ id="hor", text="&Horizontal" }
	:button{ id="diag", text="&Diagonal" }
	:button{ id="back", text="&Cancel" }
	:show()
local diag = dlg.data.diag
if not diag and not dlg.data.hor then return end

local cx = dlg.data.cx
local cy = dlg.data.cy
local cz = cy - cx
local c1 = app.fgColor.rgbaPixel
local c2 = app.bgColor.rgbaPixel
local cel = app.activeCel;
local img = cel.image:clone()
for it in img:pixels() do
	local c = it()
	local px = it.x
	local py = it.y
	if diag then
		if py - px > cz then
			if c == c1 then
				it(c2)
			elseif c == c2 then
				it(c1)
			end
		end
	else
		if px < cx then
			if c == c1 then
				it(c2)
			elseif c == c2 then
				it(c1)
			end
		end
	end
end
cel.image = img