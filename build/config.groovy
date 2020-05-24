
withConfig(configuration) {
    inline(phase: 'CONVERSION') { source, context, classNode ->
        classNode.putNodeMetaData('projectVersion', '0.4')
        classNode.putNodeMetaData('projectName', 'grails-simple-auth')
        classNode.putNodeMetaData('isPlugin', 'true')
    }
}
