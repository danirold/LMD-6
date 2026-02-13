(module
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_f32 (func (param f32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_rf32 (func (result f32)))

(type $_sig_void (func ))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $outEnt (type $_sig_i32)))
(import "runtime" "print" (func $outReal (type $_sig_f32)))

(import "runtime" "read" (func $inEnt (type $_sig_ri32)))
(import "runtime" "read" (func $inReal (type $_sig_rf32)))
(memory 2000)   ;; TODO: Esto se cambia imagino
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $principal)
(func $principal (type $_sig_void)
	(local $localsStart i32)
	(local $temp i32)
