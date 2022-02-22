package com.example.diary.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.board.Board;
import com.example.diary.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {
	
	private final BoardService boardService;
	private PythonInterpreter intPre;
	
	@GetMapping("/")
	public String saveWorldcloud() {
		File file = new File("src/wordcloud.jpg");
        FileInputStream fn = null;
        try {
            fn = new FileInputStream(file);
            int cnt = 0;
            int n = 0;
            byte bb[] = new byte [1024];

            while((n = fn.read(bb))!= -1){

                // System.out.print(n);  n 은 그냥 바이트 수를 의미하게 된다. 사실상 노의미
                System.out.write(bb,0,n);

                cnt += n; // 이러면 정상적인 바이트 수가 들어감을 확인 할 수 가 있다.
            }
            fn.close();
            System.out.print("\ncnt:" +cnt+"바이트를 읽었습니다");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fn);
		return "저장 완료";
	}
	
	// 글 목록(임시)
		@GetMapping("/test")
		public void getTest() {
			intPre = new PythonInterpreter();
			intPre.execfile("src\\main\\java\\com\\example\\diary\\web\\test.py");
			intPre.exec("print(testFunc(5,10))");

			PyFunction pyFuntion = (PyFunction) intPre.get("testFunc", PyFunction.class);
			int a = 10, b = 20;
			PyObject pyobj = pyFuntion.__call__(new PyInteger(a), new PyInteger(b));
			System.out.println(pyobj.toString());

		}
		@GetMapping("/test1")
		public void getTest2() {
			String root = System.getProperty("user.dir") + "/src/main/resources/Anlaysis/";
			String emolex = root + "emolex.xlsx";
			intPre = new PythonInterpreter();
			intPre.execfile("src\\main\\java\\com\\example\\diary\\analysis\\Analysis.py");
			
			PyFunction pyFuntion = (PyFunction) intPre.get("testFunc", PyFunction.class);
			int a = 10, b = 20;
			PyObject pyobj = pyFuntion.__call__(new PyInteger(a), new PyInteger(b));
			System.out.println(pyobj.toString());
			
//			PyFunction analysis_one_content = (PyFunction) intPre.get("analysis_one_content", PyFunction.class);
//			Board board = boardService.글상세(1);
//			PyObject pyobj = analysis_one_content.__call__(new PyString(board.getContents()), new PyString(emolex));
//			return pyobj.toString();
		}

}
