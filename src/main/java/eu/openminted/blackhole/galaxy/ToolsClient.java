package eu.openminted.blackhole.galaxy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.jersey.api.client.ClientResponse;

import eu.openminted.blackhole.galaxy.beans.History;
import eu.openminted.blackhole.galaxy.beans.Tool;
import eu.openminted.blackhole.galaxy.beans.ToolExecution;
import eu.openminted.blackhole.galaxy.beans.ToolInputs;
import eu.openminted.blackhole.galaxy.beans.ToolSection;

public interface ToolsClient {
  ToolExecution create(History history, ToolInputs inputs);
  
  /**
   * 
   * @deprecated Use {@link uploadRequest} now.
   * 
   */
  @Deprecated
  ClientResponse fileUploadRequest(String historyId,
                                   String fileType,
                                   String dbKey,
                                   File file);
  
  ClientResponse uploadRequest(FileUploadRequest request);
  
  ToolExecution upload(FileUploadRequest request);
  
  /**
   * Show details about the specified tool.
   * 
   * @param toolId the tool to look up.
   * @return details about the tool.
   */
  Tool showTool(final String toolId);
  
  /**
   * Get a list of all tools installed in Galaxy.
   * 
   * @return the list of tools installed in Galaxy.
   */
  List<ToolSection> getTools();
  
  public static class UploadFile {
    private final File file;
    private final String name;
    
    public UploadFile(final File file) {
      this(file, file.getName());
    }
    
    public UploadFile(final File file, final String name) {
      this.file = file;
      this.name =name;
    }
    
    public File getFile() {
      return file;
    }
    
    public String getName() {
      return name;
    }
    
  }
  
  public static class FileUploadRequest {
    private final String historyId;
    private final Iterable<UploadFile> files;
    private String fileType = "auto";
    private String dbKey = "?";
    private String toolId = "upload1";
    // Specify datasetName instead of file name, useful for multiple file uploads.
    private String datasetName = null;
    private Map<String, String> extraParameters = new HashMap<String, String>();
    
    public Map<String, String> getExtraParameters() {
      return extraParameters;
    }
    
    public void setExtraParameters(final Map<String, String> extraParameters) {
      this.extraParameters = extraParameters;
    }
    
    private static Iterable<UploadFile> convertFiles(final Iterable<File> files) {
      final List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
      for(final File file : files) {
        uploadFiles.add(new UploadFile(file));
      }
      return uploadFiles;
    }
    
    public FileUploadRequest(final String historyId, final File file) {
      this(historyId, convertFiles(Arrays.asList(file)));
    }
    
    public FileUploadRequest(final String historyId, final UploadFile file) {
      this(historyId, Arrays.asList(file));
    }
    
    public FileUploadRequest(final String historyId, final Iterable<UploadFile> files) {
      this.historyId = historyId;
      this.files = files;
    }
    
    public String getFileType() {
      return fileType;
    }

    public void setFileType(String fileType) {
      this.fileType = fileType;
    }

    public String getDbKey() {
      return dbKey;
    }

    public String getDatasetName() {
      return datasetName;
    }

    public void setDatasetName(String datasetName) {
      this.datasetName = datasetName;
    }

    public void setDbKey(String dbKey) {
      this.dbKey = dbKey;
    }

    public String getToolId() {
      return toolId;
    }

    public String getHistoryId() {
      return historyId;
    }

    public Iterable<UploadFile> getFiles() {
      return files;
    }
    
    public Iterable<File> getFileObjects() {
      final List<File> files = new ArrayList<File>();
      for(final UploadFile uploadFile : getFiles()) {
        files.add(uploadFile.getFile());
      }
      return files;
      
    }

    public void setToolId(String toolId) {
      this.toolId = toolId;
    }

  }
  
}
