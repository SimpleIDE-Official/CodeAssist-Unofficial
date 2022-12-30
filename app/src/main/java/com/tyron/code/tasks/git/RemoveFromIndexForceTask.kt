package com.tyron.code.tasks.git;
    
import com.tyron.builder.project.Project
import java.lang.String
import org.eclipse.jgit.api.Git
import com.blankj.utilcode.util.ThreadUtils
import com.tyron.code.util.executeAsyncProvideError
import android.widget.Toast
import com.tyron.code.ApplicationLoader
import com.tyron.code.R
import com.tyron.code.tasks.git.ErrorOutput

object RemoveFromIndexForceTask {
   
    fun remove(project:Project, path:String, name:String) {
        val future =
       executeAsyncProvideError({   
         Git.open(project.getRootFile()).rm().addFilepattern(path.toString()).call()
                return@executeAsyncProvideError   
       },    { _, _ -> })  
       
       future.whenComplete { result, error ->
       ThreadUtils.runOnUiThread {
       if (result == null || error != null) {
       ErrorOutput.ShowError(error)
       } else {   Toast.makeText(ApplicationLoader.applicationContext, name.toString() + " " + ApplicationLoader.applicationContext.getString(R.string.removed_from_index), Toast.LENGTH_SHORT).show()      }
       }
       }

    }   
}