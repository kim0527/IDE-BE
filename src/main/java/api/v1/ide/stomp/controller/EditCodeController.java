package api.v1.ide.stomp.controller;

import api.v1.ide.stomp.service.EditCodeService;
import api.v1.ide.stomp.service.dto.EditCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class EditCodeController {

    private final EditCodeService editCodeService;

    @MessageMapping("/edit") // /code/edit
    public void message(EditCodeRequest request) {
        EditCodeDto editCodeDto = new EditCodeDto(request.code(), request.roomId());
        editCodeService.sendMessage(editCodeDto);
    }
}
