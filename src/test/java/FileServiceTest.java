import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.repository.FileRepository;
import com.maxim.repository.hibernate.HibernateFileRepositoryImpl;
import com.maxim.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;
    @Mock
    private final FileRepository fileRepository = new HibernateFileRepositoryImpl();

    @Test
    void getFileByIdTest() {
        File file = new File();

        file.setId(1);
        file.setName("test");
        file.setFilePath("/test");
        file.setUpdatedAt("test");
        file.setCreateAt("test");
        file.setStatus(String.valueOf(Status.ACTIVE));

        Mockito.when(fileRepository.getById(1)).thenReturn(file);
        File expectedFile = fileService.getFileById(1);

        assertEquals(file.getId(), expectedFile.getId());
        assertEquals(file.getCreateAt(), expectedFile.getCreateAt());
        assertEquals(file.getFilePath(), expectedFile.getFilePath());
        assertEquals(file.getUpdatedAt(), expectedFile.getUpdatedAt());
        assertEquals(file.getStatus(), expectedFile.getStatus());
        assertEquals(file.getName(), expectedFile.getName());

    }

    @Test
    void getAllFilesTest() {
        File file = new File();

        List<File> files = new ArrayList<>();
        files.add(file);

        Mockito.when(fileRepository.getAll()).thenReturn(files);
        List<File> expecteFiles = fileService.getAllFiles();

        assertEquals(expecteFiles.size(), 1);
        assertEquals(expecteFiles, files);
    }

    @Test
    void saveFileTest() {
        File file = new File();

        fileService.saveFile(file);
        verify(fileRepository).save(any(File.class));
    }

    @Test
    void updateFileByIdTest() {
        File file = new File();
        File expectedFile = new File();

        expectedFile.setId(1);
        Mockito.when(fileRepository.update(file)).thenReturn(expectedFile);
        File FileFromService = fileService.updateFile(file);

        assertEquals(FileFromService.getId(), 1);
        verify(fileRepository).update(any(File.class));
    }

    @Test
    void deleteEventByIdTest() {
        fileService.deleteFileById(1);
        verify(fileRepository).deleteById(1);
    }
}