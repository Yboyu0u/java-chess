package chess;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import chess.dto.ResponseDto;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class ChessController {
    private final static ChessService chessService = new ChessService();

    public static void main(String[] args) {
        port(8082);
        staticFiles.location("/static");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("board", chessService.getBoard());

            return render(model, "index.html");
        });

        post("/move", (req, res) -> {
            final ResponseDto responseDto = chessService.move(req.body());

            return responseDto.toString();
        });

        get("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("score", chessService.status());

            return render(model, "index.html");
        });

        get("/restart", (req, res) -> {
            chessService.restart();
            res.redirect("/");
            return null;
        });

        get("/result", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("result", chessService.result());

            return render(model, "result.html");
        });

        get("/end", (req, res) -> {
            chessService.restart();
            return "게임종료되었습니다.";
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
